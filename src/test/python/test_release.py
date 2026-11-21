"""Local verification using a real git checkout and a simulated Central server."""

from contextlib import chdir
import importlib.util
import json
import os
import sys
from urllib.parse import parse_qs, urlsplit
from unittest.mock import Mock
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch
import zipfile

spec = importlib.util.spec_from_file_location("release", ".github/scripts/release.py")
release = importlib.util.module_from_spec(spec)
spec.loader.exec_module(release)
VERSION = "1.2.3"
GROUP = "es.com.kete1987"
ARTIFACT = "library"
SHA = "7" * 40
DEPLOYMENT = "d0d4fba7-c151-4576-9737-66ac09b89768"
EXPECTED = {"version": VERSION, "next_version": "1.2.4"}
PURL = f"pkg:maven/{GROUP}/{ARTIFACT}@{VERSION}"
DEPLOYMENT_NAME = f"{GROUP}:{ARTIFACT}:{VERSION} @ {SHA}"


def response(phase):
    return {"deploymentName": DEPLOYMENT_NAME, "deploymentState": phase, "purls": [PURL]}


class ReleaseTests(unittest.TestCase):
    def test_actual_pom_and_readme_version_transforms(self):
        base = release.run("git", "rev-parse", "HEAD")
        old = release.coordinates(release.pom_at(base))[2]
        new = old.removesuffix("-SNAPSHOT")
        if not old.endswith("-SNAPSHOT"):
            major, minor, patch_number = map(int, new.split("."))
            new = f"{major}.{minor}.{patch_number + 1}"
        files = release.release_files(base, None if old.endswith("-SNAPSHOT") else old, new, True)
        self.assertEqual(release.coordinates(files["pom.xml"])[2], new)
        self.assertIn(f"<version>{new}</version>", files["README.md"])
        with self.assertRaises(release.ReleaseError):
            release.release_files(base, None, "0.0.0", True)

    def test_write_phases_are_blocked_in_check_mode(self):
        for phase in ("prepare", "publish", "finish"):
            with patch.object(release.sys, "argv", ["release", phase]), \
                 patch.dict(release.os.environ, {"RELEASE_MODE": "check"}):
                with self.assertRaisesRegex(release.ReleaseError, "require publish mode"):
                    release.main()

    def test_upload_is_recorded_before_publication(self):
        events = []
        with tempfile.TemporaryDirectory() as work:
            bundle = Path(work) / "central-bundle.zip"
            root = f"es/com/kete1987/{ARTIFACT}/{VERSION}/{ARTIFACT}-{VERSION}"
            with zipfile.ZipFile(bundle, "w") as archive:
                for extension in (".pom", ".jar", "-sources.jar", "-javadoc.jar"):
                    archive.writestr(root + extension, "demo")
                    archive.writestr(root + extension + ".asc", "demo")
            with patch.object(release, "BUNDLE", bundle), \
                 patch.object(release, "github", side_effect=lambda *a: events.append(release.STATE_MARKER.search(a[2]["body"])[1])), \
                 patch.object(release, "published", side_effect=[False, True]), \
                 patch.object(release, "upload", side_effect=lambda *a: events.append("UPLOAD") or DEPLOYMENT) as upload, \
                 patch.object(release, "deployment_state", side_effect=[response("VALIDATED"), response("PUBLISHED")]), \
                 patch.object(release, "central", side_effect=lambda *a, **kw: events.append("PUBLISH")) as central, \
                 patch.object(release.time, "sleep"):
                release.ensure_published({"number": 1}, EXPECTED.copy(), GROUP, ARTIFACT, VERSION, SHA, None, False)
            upload.assert_called_once_with(bundle, DEPLOYMENT_NAME)
            central.assert_called_once_with(f"deployment/{DEPLOYMENT}", "POST", statuses=(204,))
        self.assertIn('"upload_started": true', events[0])
        self.assertEqual(events[1], "UPLOAD")
        self.assertIn(DEPLOYMENT, events[2])
        self.assertEqual(events[3], "PUBLISH")

    def test_resume_never_reuploads_an_existing_published_deployment(self):
        state = {**EXPECTED, "upload_started": True, "deployment": DEPLOYMENT}
        with patch.object(release, "github"), patch.object(release, "published", return_value=True), \
             patch.object(release, "deployment_state", return_value=response("PUBLISHED")), \
             patch.object(release, "upload") as upload, patch.object(release, "central") as central:
            release.ensure_published({"number": 1}, state, GROUP, ARTIFACT, VERSION, SHA, None, False)
            upload.assert_not_called()
            central.assert_not_called()

    def test_ambiguous_upload_and_existing_unrelated_version_stop(self):
        for state, existing in (({**EXPECTED, "upload_started": True}, False), (EXPECTED.copy(), True)):
            with patch.object(release, "published", return_value=existing), \
                 patch.object(release, "upload") as upload, patch.object(release, "github") as github:
                with self.assertRaises(release.ReleaseError):
                    release.ensure_published({"number": 1}, state, GROUP, ARTIFACT, VERSION, SHA, None, False)
                upload.assert_not_called()
                github.assert_not_called()

    def test_published_deployment_must_have_the_expected_coordinates(self):
        status = {**response("PUBLISHED"), "purls": ["pkg:maven/another/library@1.2.3"]}
        with patch.object(release, "github"), patch.object(release, "deployment_state", return_value=status), \
             patch.object(release, "central") as central:
            with self.assertRaisesRegex(release.ReleaseError, "Unexpected coordinates"):
                release.ensure_published(
                    {"number": 1}, {**EXPECTED, "deployment": DEPLOYMENT}, GROUP, ARTIFACT, VERSION, SHA, None, False
                )
            central.assert_not_called()


class ReleaseSimulation:
    """Use a local Git remote and simulate only the GitHub and Central services."""

    def __init__(self, origin, group, artifact, version):
        self.git = release.run
        self.origin = origin
        self.coordinate = f"pkg:maven/{group}/{artifact}@{version}"
        self.pulls = {}
        self.releases = set()
        self.events = []
        self.deployment = None
        self.merging = None

    def run(self, *args):
        if args[:3] == ("gh", "pr", "merge"):
            number = int(args[3])
            pull = self.pulls[number]
            expected_head = args[args.index("--match-head-commit") + 1]
            assert expected_head == pull["head"]["sha"]
            self.merging = number
            return ""
        if args[:3] == ("gh", "release", "create"):
            assert self.deployment["deploymentState"] == "PUBLISHED"
            assert args[3] not in self.releases
            self.releases.add(args[3])
            return ""
        return self.git(*args)

    def request(self, host, method, path, token, body=None, content_type=release.JSON, statuses=(200,)):
        url = urlsplit(path)
        query = parse_qs(url.query)
        if host == "central.sonatype.com":
            assert token == "dHJpYWw6dHJpYWw="
            return self.central(method, url.path.removeprefix("/api/v1/publisher/"), query, body, content_type)
        assert host == "api.github.com" and token == "mock-github-token"
        route = url.path.removeprefix("/repos/local/library/")
        return self.github(method, route, query, body)

    def central(self, method, route, query, body, content_type):
        if (method, route) == ("GET", "published"):
            published = bool(self.deployment and self.deployment["deploymentState"] == "PUBLISHED")
            return 200, json.dumps({"published": published}).encode()
        if (method, route) == ("POST", "upload"):
            assert self.deployment is None
            assert query["publishingType"] == ["USER_MANAGED"]
            assert content_type.startswith("multipart/form-data; boundary=")
            assert b'filename="central-bundle.zip"' in body
            assert b"Content-Type: application/octet-stream" in body
            assert 'upload_started' in self.pulls[1]["body"]
            self.deployment = {
                "deploymentName": query["name"][0],
                "deploymentState": "VALIDATED",
                "purls": [self.coordinate],
            }
            self.events.append("upload")
            return 201, DEPLOYMENT.encode()
        if (method, route) == ("POST", "status"):
            assert query["id"] == [DEPLOYMENT]
            return 200, json.dumps(self.deployment).encode()
        if (method, route) == ("POST", f"deployment/{DEPLOYMENT}"):
            assert DEPLOYMENT in self.pulls[1]["body"]
            self.deployment["deploymentState"] = "PUBLISHED"
            self.events.append("publish")
            return 204, b""
        raise AssertionError((method, route))

    def github(self, method, route, query, body):
        if (method, route) == ("GET", "pulls"):
            branch = query["head"][0].split(":")[1]
            return 200, json.dumps([pr for pr in self.pulls.values() if pr["head"]["ref"] == branch]).encode()
        if (method, route) == ("POST", "pulls"):
            data = json.loads(body)
            number = len(self.pulls) + 1
            head = self.git("git", "--git-dir", self.origin, "rev-parse", f"refs/heads/{data['head']}")
            self.pulls[number] = {
                "number": number, "body": data["body"], "user": {"login": release.BOT},
                "head": {"ref": data["head"], "sha": head, "repo": {"full_name": "local/library"}},
                "base": {"ref": data["base"]}, "state": "open", "merged_at": None,
                "html_url": f"https://example.invalid/pull/{number}",
            }
            return 201, json.dumps(self.pulls[number]).encode()
        if route.startswith("pulls/"):
            number = int(route.split("/")[1])
            pull = self.pulls[number]
            if method == "PATCH":
                pull["body"] = json.loads(body)["body"]
            elif self.merging == number:
                sha = pull["head"]["sha"]
                self.git("git", "--git-dir", self.origin, "update-ref", "refs/heads/master", sha)
                pull.update(state="closed", merged_at="test-time", merge_commit_sha=sha)
                self.merging = None
            return 200, json.dumps(pull).encode()
        if route.startswith("releases/tags/"):
            return (200, b'{"draft": false, "prerelease": false}') if route.split("/")[-1] in self.releases else (404, b"")
        raise AssertionError((method, route))


class WorkflowTests(unittest.TestCase):
    def test_release_and_repeat_against_local_git(self):
        repository = release.run("git", "rev-parse", "--show-toplevel")
        tested_commit = release.run("git", "rev-parse", "HEAD")
        with tempfile.TemporaryDirectory() as directory:
            origin = str(Path(directory) / "origin.git")
            checkout = Path(directory) / "checkout"
            release.run("git", "clone", "--bare", "--no-tags", repository, origin)
            release.run("git", "--git-dir", origin, "update-ref", "refs/heads/master", tested_commit)
            release.run("git", "--git-dir", origin, "symbolic-ref", "HEAD", "refs/heads/master")
            release.run("git", "clone", origin, str(checkout))
            with chdir(checkout):
                base = release.run("git", "rev-parse", "HEAD")
                group, artifact, current = release.coordinates(release.pom_at(base))
                planned = current.removesuffix("-SNAPSHOT")
                if not current.endswith("-SNAPSHOT"):
                    snapshot = release.release_files(base, current, current + "-SNAPSHOT", False)
                    Path("pom.xml").write_text(snapshot["pom.xml"], encoding="utf-8")
                    release.run("git", "-c", "user.name=Test", "-c", "user.email=test@example.invalid",
                                "commit", "-am", "Set initial snapshot for the test")
                    release.run("git", "push", "origin", "HEAD:refs/heads/master")
                    base = release.run("git", "rev-parse", "HEAD")
                major, minor, patch_number = map(int, planned.split("."))
                after = f"{major}.{minor}.{patch_number + 1}"
                output = Path(directory) / "outputs"
                simulation = ReleaseSimulation(origin, group, artifact, planned)
                env = {
                    "GITHUB_REPOSITORY": "local/library", "GH_TOKEN": "mock-github-token",
                    "MAVEN_CENTRAL_USERNAME": "trial", "MAVEN_CENTRAL_PASSWORD": "trial",
                    "RELEASE_VERSION": planned, "NEXT_VERSION": after, "RELEASE_MODE": "publish",
                    "GITHUB_OUTPUT": str(output), "RECOVERY_DEPLOYMENT_ID": "", "RETRY_UPLOAD": "false",
                }
                with patch.dict(os.environ, env), patch.object(release, "run", side_effect=simulation.run), \
                     patch.object(release, "request", side_effect=simulation.request), \
                     patch.object(release.time, "sleep"):
                    def phase(name):
                        with patch.object(sys, "argv", ["release", name]):
                            release.main()

                    phase("check")
                    self.assertEqual(simulation.pulls, {})
                    self.assertEqual(release.refresh_base(), base)
                    branch = f"release/v{planned}"
                    candidate_head = release.push_version(f"refs/heads/{branch}", f"Release {planned}", None, planned, True)
                    with self.assertRaisesRegex(release.ReleaseError, "unexpected changes"):
                        release.check_branch(branch, candidate_head, None, after, True)
                    phase("prepare")
                    candidate = simulation.pulls[1]["merge_commit_sha"]
                    self.assertEqual(release.coordinates(release.pom_at(candidate))[2], planned)
                    self.assertEqual(release.run("git", "rev-parse", "HEAD"), base)
                    self.assertIn("bundle=true", output.read_text())
                    bundle = release.BUNDLE
                    bundle.parent.mkdir(parents=True)
                    root = f"{group.replace('.', '/')}/{artifact}/{planned}/{artifact}-{planned}"
                    with zipfile.ZipFile(bundle, "w") as archive:
                        for extension in (".pom", ".jar", "-sources.jar", "-javadoc.jar"):
                            archive.writestr(root + extension, "fixture")
                            archive.writestr(root + extension + ".asc", "fixture")
                    phase("publish")
                    phase("finish")
                    phase("check")
                    phase("prepare")
                    phase("publish")
                    phase("finish")
                    self.assertIn("bundle=false", output.read_text())
                    self.assertEqual(simulation.events, ["upload", "publish"])
                    self.assertEqual(simulation.releases, {f"v{planned}"})
                    self.assertEqual(len(simulation.pulls), 2)
                    self.assertEqual(release.tag_target(f"v{planned}"), candidate)
                    self.assertEqual(release.coordinates(release.pom_at(release.refresh_base()))[2], after + "-SNAPSHOT")

    def test_http_error_reports_status_without_exposing_response(self):
        connection = Mock()
        connection.getresponse.return_value.status = 403
        connection.getresponse.return_value.read.return_value = b"Must not appear in the exception"
        with patch.object(release.http.client, "HTTPSConnection", return_value=connection):
            with self.assertRaisesRegex(release.ReleaseError, r"example\.invalid GET /test returned HTTP 403"):
                release.request("example.invalid", "GET", "/test", "mock-token")
            connection.close.assert_called_once()

    def test_http_success_and_missing_central_credentials(self):
        connection = Mock()
        connection.getresponse.return_value.status = 200
        connection.getresponse.return_value.read.return_value = b'{"published": true}'
        with patch.object(release.http.client, "HTTPSConnection", return_value=connection):
            self.assertEqual(release.request("example.invalid", "GET", "/test", "mock-token"),
                             (200, b'{"published": true}'))
        with patch.dict(os.environ, {"MAVEN_CENTRAL_USERNAME": "", "MAVEN_CENTRAL_PASSWORD": ""}):
            with self.assertRaisesRegex(release.ReleaseError, "credentials are missing"):
                release.published(GROUP, ARTIFACT, VERSION)

    def test_recover_or_retry_a_previous_deployment(self):
        pull = {"number": 1}
        state = {**EXPECTED, "upload_started": True, "deployment": DEPLOYMENT}
        with patch.object(release, "deployment_state", return_value=response("FAILED")):
            self.assertIsNone(release.select_deployment(pull, state, DEPLOYMENT_NAME, None, True))
            self.assertEqual(release.select_deployment(pull, {"upload_started": True}, DEPLOYMENT_NAME,
                                                      DEPLOYMENT, False), DEPLOYMENT)
            with self.assertRaisesRegex(release.ReleaseError, "already records deployment"):
                release.select_deployment(pull, state, DEPLOYMENT_NAME, "different-id", False)
        with patch.object(release, "deployment_state", return_value=response("PUBLISHING")):
            with self.assertRaisesRegex(release.ReleaseError, "only for failed or unrecorded"):
                release.select_deployment(pull, state, DEPLOYMENT_NAME, None, True)

    def test_conflicting_or_invalid_release_inputs(self):
        env = {"RELEASE_VERSION": "1.2.3", "NEXT_VERSION": "1.2.2"}
        with patch.dict(os.environ, env):
            with self.assertRaisesRegex(release.ReleaseError, "must be higher"):
                release.release_inputs("check")
        with patch.dict(os.environ, {**env, "NEXT_VERSION": "v1.2.4"}):
            with self.assertRaisesRegex(release.ReleaseError, "must use X.Y.Z"):
                release.release_inputs("check")
        with patch.dict(os.environ, {
            **env, "NEXT_VERSION": "1.2.4", "RECOVERY_DEPLOYMENT_ID": DEPLOYMENT, "RETRY_UPLOAD": "true",
        }):
            with self.assertRaisesRegex(release.ReleaseError, "Choose either"):
                release.release_inputs("check")


if __name__ == "__main__":
    unittest.main()
