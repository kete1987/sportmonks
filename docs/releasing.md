# Releasing to Maven Central

From **Actions → Maven release → Run workflow**, select `master` and enter the stable `version` and the `next_version` as `X.Y.Z`. The workflow appends `-SNAPSHOT` to the development version. Only `kete1987` can start or retry the workflow.

- **check** is the default. It checks the version inputs, the release PR and tag when present, and Maven Central's publication status. It does not change GitHub or upload an artifact.
- **Release dry run** separately checks the GitHub App and Central credentials, builds the signed bundle, and verifies its signatures and checksums with uploading disabled.
- **publish** runs the release. Use it only when you intend to publish the version.

The workflow opens a release PR with the stable version in the POM and README and waits for GitHub to merge it after the required `build` check. It builds and signs that exact merge commit, uploads the bundle as a user-managed deployment, and records the deployment ID on the release PR before requesting publication. Once Central reports `PUBLISHED` and confirms the version is available, it tags the commit as `vX.Y.Z`, creates the GitHub Release with generated notes, and opens the auto-merge PR for the next SNAPSHOT.

## Configuration

The `maven-central` GitHub environment is restricted to `master`. It needs `RELEASE_APP_CLIENT_ID` as a variable and these secrets.

| Secret | Purpose |
|---|---|
| `RELEASE_APP_PRIVATE_KEY` | PEM for `imoralesalo Bot`, installed on this repo with write access to Contents and Pull requests |
| `MAVEN_CENTRAL_USERNAME` and `MAVEN_CENTRAL_PASSWORD` | Publisher Portal token pair with access to this library's namespace |
| `MAVEN_GPG_PRIVATE_KEY` and `MAVEN_GPG_PASSPHRASE` | ASCII-armored signing key and its passphrase |

Keep the `build` check required on `master` and auto-merge enabled. For a key rotation, update the signing fingerprint in the dry run and make the new public key available on a [keyserver supported by Central](https://central.sonatype.org/publish/requirements/gpg/#distributing-your-public-key).

## If a release fails

Open the failed Actions job, then find the release PR by its branch `release/vX.Y.Z`. Resume with the same `version` and `next_version` in `publish` mode. The workflow reuses its PRs, deployment and tag and checks that they belong to the same release.

| Where it failed | What to check |
|---|---|
| App authentication or opening a PR | Check the PEM, the Client ID, this repo's App installation and its granted permissions. |
| Waiting for a PR to merge | Inspect the PR's `build` check, merge conflicts and auto-merge status. If the PR was closed, reopen it before resuming. Avoid editing the automation branches. |
| Bundle or GPG | Inspect Maven's failing goal. Compare the signing key and passphrase with the working dry run. |
| Central authentication | Check the token pair, expiry and `es.com.kete1987` namespace permissions in the Publisher Portal. |
| Central validation reports `FAILED` | Open the deployment in the Portal and read the validation errors. If the same commit can be rebuilt after correcting credentials or configuration, resume with `retry_upload` selected. If the source needs to change, resolve the existing release PR and Maven version before starting a different release. |
| Upload interrupted before saving an ID | In the Portal, look for `groupId:artifactId:version @ commit`, matching the release PR's merge SHA. If it exists, resume with that UUID in `deployment_id`. If you confirm no deployment was created, resume with `retry_upload` selected. |
| Central already published but GitHub or SNAPSHOT failed | Resume with the same inputs. The saved deployment ID lets the workflow confirm publication before retrying the GitHub steps. Never move an existing tag to a different commit. |

Inspect the deployment for details at the [Central Publisher Portal](https://central.sonatype.com/publishing/deployments). Maven Central release versions are immutable.
