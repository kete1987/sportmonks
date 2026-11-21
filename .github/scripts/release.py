"""Drive the protected GitHub PRs and the Sonatype deployment for a release."""

import argparse
import base64
import http.client
import json
import os
from pathlib import Path
import re
import subprocess
import sys
import time
from urllib.parse import urlencode
import uuid
import xml.etree.ElementTree as ET
import zipfile

BASE = "master"
BOT = "imoralesalo-bot[bot]"
VERSION_RE = r"(?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*)"
POM_VERSION = re.compile(r"(?m)^(    <version>)([^<]+)(</version>)$")
STATE_MARKER = re.compile(r"(?m)^<!-- maven-release: (.+) -->$")
BUNDLE = Path("candidate/target/central-publishing/central-bundle.zip")


class ReleaseError(Exception):
    pass


def run(*args):
    return subprocess.check_output(args, text=True).strip()


def request(host, method, path, token, body=None, content_type="application/json", statuses=(200,)):
    headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type": content_type,
        "User-Agent": "Maven library release (GitHub Actions)",
        "Accept": "application/json",
    }
    if host == "api.github.com":
        headers["X-GitHub-Api-Version"] = "2022-11-28"
    connection = http.client.HTTPSConnection(host, timeout=60)
    try:
        connection.request(method, path, body=body, headers=headers)
        response = connection.getresponse()
        data = response.read()
        if response.status not in statuses:
            raise ReleaseError(f"{host} {method} {path} returned HTTP {response.status}")
        return response.status, data
    finally:
        connection.close()


def github(path, method="GET", payload=None, statuses=(200,)):
    body = json.dumps(payload).encode() if payload is not None else None
    status, data = request(
        "api.github.com", method, f"/repos/{os.environ['GITHUB_REPOSITORY']}/{path}",
        os.environ["GH_TOKEN"], body, statuses=statuses,
    )
    return None if status == 404 else json.loads(data)


def central(path, method="GET", body=None, content_type="application/json", statuses=(200,)):
    username = os.environ["MAVEN_CENTRAL_USERNAME"]
    password = os.environ["MAVEN_CENTRAL_PASSWORD"]
    if not username or not password:
        raise ReleaseError("Maven Central credentials are missing")
    token = base64.b64encode(f"{username}:{password}".encode()).decode()
    return request(
        "central.sonatype.com", method, f"/api/v1/publisher/{path}", token,
        body, content_type, statuses,
    )[1]


def published(group, artifact, version):
    query = urlencode({"namespace": group, "name": artifact, "version": version})
    result = json.loads(central(f"published?{query}"))
    if type(result.get("published")) is not bool:
        raise ReleaseError("Maven Central did not return a publication status")
    return result["published"]


def pom_at(commit):
    return run("git", "show", f"{commit}:pom.xml")


def coordinates(pom):
    project = ET.fromstring(pom)
    values = tuple(project.findtext(f"{{*}}{key}") for key in ("groupId", "artifactId", "version"))
    if not all(values):
        raise ReleaseError("Expected a project groupId, artifactId and version in pom.xml")
    return values


def refresh_base():
    run("git", "fetch", "origin", f"+refs/heads/{BASE}:refs/remotes/origin/{BASE}")
    return run("git", "rev-parse", f"origin/{BASE}")


def release_files(parent, old, new, update_readme):
    pom = pom_at(parent)
    group, artifact, actual = coordinates(pom)
    if old is None:
        planned = actual.removesuffix("-SNAPSHOT")
        if not actual.endswith("-SNAPSHOT") or not re.fullmatch(VERSION_RE, planned):
            raise ReleaseError(f"The POM must have a development SNAPSHOT version; found {actual}")
        if tuple(map(int, new.split("."))) < tuple(map(int, planned.split("."))):
            raise ReleaseError(f"Release {new} is older than the planned version {planned}")
    elif actual != old:
        raise ReleaseError(f"The POM is {actual}; expected {old}")
    modified, count = POM_VERSION.subn(lambda match: f"{match[1]}{new}{match[3]}", pom)
    if count != 1:
        raise ReleaseError("Could not replace the project's single version in pom.xml")
    files = {"pom.xml": modified + "\n"}
    if update_readme:
        readme = run("git", "show", f"{parent}:README.md")
        dependency = re.compile(
            rf"(<groupId>{re.escape(group)}</groupId>\s*"
            rf"<artifactId>{re.escape(artifact)}</artifactId>\s*<version>)[^<]+(</version>)"
        )
        updated, count = dependency.subn(lambda match: f"{match[1]}{new}{match[2]}", readme)
        if not count:
            raise ReleaseError("The README Maven dependency could not be located")
        if updated != readme:
            files["README.md"] = updated + "\n"
    return files


def body(state):
    return (
        "Managed by the Maven release workflow. See docs/releasing.md for recovery.\n\n"
        f"<!-- maven-release: {json.dumps(state, sort_keys=True)} -->"
    )


def read_state(pull, expected):
    marker = STATE_MARKER.search(pull["body"] or "")
    if pull["user"]["login"] != BOT or not marker or pull["base"]["ref"] != BASE:
        raise ReleaseError(f"PR #{pull['number']} is not a release PR created by {BOT}")
    state = json.loads(marker[1])
    if any(state.get(key) != value for key, value in expected.items()):
        raise ReleaseError(f"PR #{pull['number']} records different release inputs. Check its body.")
    return state


def find_pull(branch, state):
    repo = os.environ["GITHUB_REPOSITORY"]
    params = urlencode({"state": "all", "head": f"{repo.split('/')[0]}:{branch}", "base": BASE})
    pulls = github(f"pulls?{params}")
    if len(pulls) > 1:
        raise ReleaseError(f"Multiple PRs found for {branch}")
    if pulls:
        pull = pulls[0]
        read_state(pull, state)
        if (pull["head"]["repo"] or {}).get("full_name") != repo:
            raise ReleaseError(f"PR #{pull['number']} does not use a branch in {repo}")
        if pull["state"] == "closed" and not pull["merged_at"]:
            raise ReleaseError(f"PR #{pull['number']} was closed without merging. Reopen it to continue.")
        return pull
    return None


def remote_heads(ref):
    return {
        name: sha
        for line in run("git", "ls-remote", "origin", ref, f"{ref}^{{}}").splitlines()
        for sha, name in [line.split()]
    }


def push(ref):
    run("git", "-c", "credential.helper=", "-c", "credential.helper=!gh auth git-credential",
        "push", "origin", ref)


def ensure_pull(branch, title, state, old, new, update_readme=False):
    pull = find_pull(branch, state)
    if pull and pull["merged_at"]:
        return pull
    if pull:
        head = pull["head"]["sha"]
    else:
        ref = f"refs/heads/{branch}"
        head = remote_heads(ref).get(ref)
    if head:
        run("git", "fetch", "origin", head)
        parent = run("git", "rev-parse", f"{head}^")
        expected = release_files(parent, old, new, update_readme)
        changed = run("git", "diff", "--name-only", parent, head).splitlines()
        if set(changed) != set(expected) or any(
            run("git", "show", f"{head}:{name}") != content.strip()
            for name, content in expected.items()
        ):
            raise ReleaseError(f"{branch} has unexpected changes. Check the branch and PR.")
    else:
        parent = refresh_base()
        expected = release_files(parent, old, new, update_readme)
        workflow_sha = run("git", "rev-parse", "HEAD")
        run("git", "switch", "--detach", parent)
        try:
            for name, content in expected.items():
                Path(name).write_text(content, encoding="utf-8")
            run("git", "add", "--", *expected)
            run("git", "-c", f"user.name={BOT}", "-c", f"user.email={BOT}@users.noreply.github.com",
                "commit", "-m", title)
            head = run("git", "rev-parse", "HEAD")
            push(f"{head}:refs/heads/{branch}")
        finally:
            run("git", "switch", "--detach", workflow_sha)
    if not pull:
        pull = github(
            "pulls", "POST", {"head": branch, "base": BASE, "title": title, "body": body(state)},
            statuses=(201,),
        )
        read_state(pull, state)
    number = pull["number"]
    print(f"Waiting for the required checks and auto-merge of {pull['html_url']}", flush=True)
    try:
        run("gh", "pr", "merge", str(number), "--repo", os.environ["GITHUB_REPOSITORY"],
            "--auto", "--squash", "--match-head-commit", head)
    except subprocess.CalledProcessError:
        if not github(f"pulls/{number}")["merged_at"]:
            raise
    for _ in range(60):
        pull = github(f"pulls/{number}")
        if pull["head"]["sha"] != head:
            raise ReleaseError(f"PR #{number} changed after the merge was requested")
        if pull["merged_at"]:
            return pull
        if pull["state"] == "closed":
            raise ReleaseError(f"PR #{number} was closed without merging")
        time.sleep(10)
    raise ReleaseError(f"PR #{number} has not merged; check build, conflicts and auto-merge before retrying")


def output(**values):
    with open(os.environ["GITHUB_OUTPUT"], "a", encoding="utf-8") as target:
        for key, value in values.items():
            target.write(f"{key}={value}\n")


def deployment_state(deployment_id, name):
    query = urlencode({"id": deployment_id})
    result = json.loads(central(f"status?{query}", "POST"))
    if result["deploymentName"] != name:
        raise ReleaseError(f"Deployment {deployment_id} belongs to a different release commit")
    return result


def upload(bundle, name):
    boundary = "maven-release-" + uuid.uuid4().hex
    header = (
        f"--{boundary}\r\nContent-Disposition: form-data; name=\"bundle\"; "
        'filename="central-bundle.zip"\r\nContent-Type: application/octet-stream\r\n\r\n'
    ).encode()
    payload = header + bundle.read_bytes() + f"\r\n--{boundary}--\r\n".encode()
    query = urlencode({"name": name, "publishingType": "USER_MANAGED"})
    data = central(
        f"upload?{query}", "POST", payload, f"multipart/form-data; boundary={boundary}", (201,),
    )
    return str(uuid.UUID(data.decode().strip()))


def ensure_published(pull, state, group, artifact, version, sha, recovery_id, retry_upload):
    number = pull["number"]
    name = f"{group}:{artifact}:{version} @ {sha}"
    purl = f"pkg:maven/{group}/{artifact}@{version}"
    deployment_id = state.get("deployment")
    if recovery_id and deployment_id and recovery_id != deployment_id:
        raise ReleaseError(f"PR #{number} already records deployment {deployment_id}")
    deployment_id = deployment_id or recovery_id
    status = deployment_state(deployment_id, name) if deployment_id else None
    if retry_upload:
        failed = status and status["deploymentState"] == "FAILED"
        unrecorded = not deployment_id and state.get("upload_started")
        if not (failed or unrecorded):
            raise ReleaseError("retry_upload is only for failed or unrecorded uploads")
        deployment_id = None
        status = None
    if not deployment_id:
        if published(group, artifact, version):
            raise ReleaseError("This version already exists in Central. Recover the original deployment ID.")
        if state.get("upload_started") and not retry_upload:
            raise ReleaseError("Previous upload has no recorded ID. Inspect the Portal and see docs/releasing.md.")
        with zipfile.ZipFile(BUNDLE) as bundle:
            artifact_path = f"{group.replace('.', '/')}/{artifact}/{version}/{artifact}-{version}"
            for extension in (".pom", ".jar", "-sources.jar", "-javadoc.jar"):
                bundle.getinfo(artifact_path + extension)
                bundle.getinfo(artifact_path + extension + ".asc")
        state["upload_started"] = True
        state.pop("deployment", None)
        github(f"pulls/{number}", "PATCH", {"body": body(state)})
        print(f"Uploading deployment named {name}", flush=True)
        deployment_id = upload(BUNDLE, name)
    state["deployment"] = deployment_id
    print(f"Sonatype deployment ID: {deployment_id}", flush=True)
    github(f"pulls/{number}", "PATCH", {"body": body(state)})

    publish_requested = False
    deadline = time.monotonic() + 25 * 60
    while time.monotonic() < deadline:
        status = deployment_state(deployment_id, name)
        phase = status["deploymentState"]
        print(f"Sonatype: {phase}", flush=True)
        if phase == "FAILED":
            raise ReleaseError(f"Sonatype validation failed for {deployment_id}. See the Portal for errors.")
        if phase in {"VALIDATED", "PUBLISHED"}:
            if set(status.get("purls") or []) != {purl}:
                raise ReleaseError(f"Unexpected coordinates in Sonatype deployment {deployment_id}")
            if phase == "PUBLISHED":
                if published(group, artifact, version):
                    return
            elif not publish_requested:
                central(f"deployment/{deployment_id}", "POST", statuses=(204,))
                publish_requested = True
        elif phase not in {"PENDING", "VALIDATING", "PUBLISHING"}:
            raise ReleaseError(f"Unknown deployment state {phase}")
        time.sleep(10)
    raise ReleaseError(f"Timed out waiting for deployment {deployment_id}. Retry the same release to resume.")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("phase", choices=("check", "prepare", "publish", "finish"))
    options = parser.parse_args()
    if options.phase != "check" and os.environ.get("RELEASE_MODE") != "publish":
        raise ReleaseError("Release changes require publish mode")
    version = os.environ["RELEASE_VERSION"]
    next_version = os.environ["NEXT_VERSION"]
    if not all(re.fullmatch(VERSION_RE, value) for value in (version, next_version)):
        raise ReleaseError("version and next_version must use X.Y.Z without a v or -SNAPSHOT")
    if tuple(map(int, next_version.split("."))) <= tuple(map(int, version.split("."))):
        raise ReleaseError("next_version must be higher than version")
    release_branch = f"release/v{version}"
    expected = {"version": version, "next_version": next_version}
    recovery_id = os.environ.get("RECOVERY_DEPLOYMENT_ID") or None
    retry_upload = os.environ.get("RETRY_UPLOAD") == "true"
    if recovery_id:
        recovery_id = str(uuid.UUID(recovery_id))
    if recovery_id and retry_upload:
        raise ReleaseError("Choose either deployment_id or retry_upload")
    base = refresh_base()
    pull = find_pull(release_branch, expected)
    state = read_state(pull, expected) if pull else expected.copy()

    if options.phase == "check":
        current = coordinates(pom_at(base))[2]
        if pull and pull["merged_at"]:
            sha = pull["merge_commit_sha"]
            run("git", "merge-base", "--is-ancestor", sha, base)
            group, artifact, actual = coordinates(pom_at(sha))
            if actual != version:
                raise ReleaseError(f"Release commit {sha} has version {actual}, expected {version}")
        else:
            if recovery_id or retry_upload:
                raise ReleaseError("Upload recovery requires the original merged release PR")
            group, artifact, _ = coordinates(pom_at(base))
            release_files(base, None, version, True)
        if state.get("upload_started") and not state.get("deployment") and not recovery_id and not retry_upload:
            raise ReleaseError("Previous upload has no recorded ID. Inspect the Portal and see docs/releasing.md.")
        if published(group, artifact, version) and not state.get("deployment") and not recovery_id:
            raise ReleaseError("This version already exists in Central without a recorded deployment")
        tag_ref = f"refs/tags/v{version}"
        tag_heads = remote_heads(tag_ref)
        tagged = tag_heads.get(f"{tag_ref}^{{}}") or tag_heads.get(tag_ref)
        if tagged and (not pull or not pull["merged_at"] or tagged != pull["merge_commit_sha"]):
            raise ReleaseError(f"Tag v{version} already exists on a different commit")
        print(f"Checked {group}:{artifact}:{version}; {BASE} is {current}; next is {next_version}-SNAPSHOT")
        return

    if options.phase == "prepare":
        pull = ensure_pull(release_branch, f"Release {version}", expected, None, version, True)
        state = read_state(pull, expected)
        output(
            sha=pull["merge_commit_sha"],
            bundle=str(retry_upload or not (recovery_id or state.get("deployment"))).lower(),
        )
        return

    if not pull or not pull["merged_at"]:
        raise ReleaseError("The release PR must merge before publishing")
    sha = pull["merge_commit_sha"]
    run("git", "merge-base", "--is-ancestor", sha, base)
    group, artifact, actual = coordinates(pom_at(sha))
    if actual != version:
        raise ReleaseError(f"Release commit {sha} has version {actual}, expected {version}")
    if options.phase == "publish":
        ensure_published(pull, state, group, artifact, version, sha, recovery_id, retry_upload)
        return

    tag = f"v{version}"
    ref = f"refs/tags/{tag}"
    heads = remote_heads(ref)
    tagged = heads.get(f"{ref}^{{}}") or heads.get(ref)
    if tagged and tagged != sha:
        raise ReleaseError(f"Tag {tag} points to another commit")
    if not tagged:
        push(f"{sha}:{ref}")
    release = github(f"releases/tags/{tag}", statuses=(200, 404))
    if release is None:
        run("gh", "release", "create", tag, "--repo", os.environ["GITHUB_REPOSITORY"],
            "--title", tag, "--generate-notes", "--verify-tag")
    elif release["draft"] or release["prerelease"]:
        raise ReleaseError(f"GitHub release {tag} exists as a draft or prerelease")
    snapshot = ensure_pull(
        f"release/after-v{version}", f"Prepare {next_version}-SNAPSHOT", expected,
        version, f"{next_version}-SNAPSHOT",
    )
    snapshot_sha = snapshot["merge_commit_sha"]
    run("git", "merge-base", "--is-ancestor", snapshot_sha, refresh_base())
    snapshot_version = coordinates(pom_at(snapshot_sha))[2]
    if snapshot_version != f"{next_version}-SNAPSHOT":
        raise ReleaseError(f"Snapshot PR merged with unexpected version {snapshot_version}")
    print(f"Release {tag} published. Development continues at {snapshot_version}.")


if __name__ == "__main__":
    try:
        main()
    except (
        ReleaseError, OSError, ValueError, KeyError, http.client.HTTPException,
        subprocess.CalledProcessError, zipfile.BadZipFile, ET.ParseError,
    ) as error:
        sys.exit(str(error))
