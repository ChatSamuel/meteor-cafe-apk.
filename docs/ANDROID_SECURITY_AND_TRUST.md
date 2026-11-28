# Android Security and Distribution

## Objective

Provide a transparent, verifiable Android build of Meteor Cafe and reduce avoidable installation warnings without weakening Android security protections.

No developer can guarantee that Google Play Protect will never display a warning. Its decisions may depend on the APK contents, permissions, signing identity, distribution history and Google's classification systems. The project will address warnings through verifiable build practices and Google's review process, not by asking users to disable protection.

## Current build facts

The repository's current GitHub Actions workflow invokes `:app:assembleMeteorDebug`. The Gradle project defines a separate `release` build type with code shrinking and resource shrinking, and a `release` signing configuration that reads `keystore.properties` when that file is present. The CI workflow currently does not configure a project-owned release key.

The inherited J2ME-Loader manifest declares permissions for camera, microphone, notifications, legacy external storage, coarse and fine location, Internet access, Bluetooth and shortcut installation, in addition to vibration. These declarations belong to the general-purpose runtime. Their necessity for the bundled Meteor Cafe experience must be checked before a public release; they should not be removed blindly because runtime features and saved data must continue to work.

## Release-readiness plan

### 1. Audit the exact APK that will be distributed

For every candidate build, record and verify:

- the effective application ID, version name and version code;
- the merged manifest and the final permission list;
- the APK signing certificate fingerprint and signature schemes;
- the APK/ZIP structure and alignment;
- the SHA-256 hash of the final APK.

The effective application ID must be read from the built APK, not inferred from the product-flavor configuration alone. Keep the package name stable once a public release is established.

### 2. Review permissions for the dedicated game

Inspect the merged manifest for the `meteor` flavor. Confirm which runtime permissions are needed by the bundled game and remove only those that are demonstrably unused by the dedicated application. Preserve the permissions and APIs required for audio playback, touch input, local game data and normal application operation.

The dedicated build should not request SMS, call-log, accessibility-service or notification-listener access. These capabilities are not part of Meteor Cafe's gameplay and must not be introduced.

### 3. Establish a permanent release-signing identity

Create a project-owned Android release keystore under the maintainer's control. Keep an offline, encrypted backup. Never commit the keystore, passwords or private key to GitHub.

Store the keystore and its credentials as GitHub Actions secrets. Configure the release workflow to build the `meteorRelease` variant and sign it with that key. Keep the same signing identity for future updates; changing the key can prevent Android from installing an update over an existing copy.

Before replacing the current public download, verify the package name and signing-certificate fingerprint against the APK already distributed. Do not silently change either value.

### 4. Validate each release artifact

The release pipeline should:

- run the Android build from the reviewed source tree;
- verify the expected original and integrated game-JAR hashes;
- run `zipalign` validation and `apksigner verify`;
- print the application ID, version and signing-certificate fingerprint;
- extract and record the final permission list;
- calculate and publish the APK SHA-256 hash;
- retain the exact source commit associated with the APK.

Run a clean-install test and an in-place update test on the Samsung Galaxy A05 before publishing the artifact. Exercise game launch, touch controls, sound, saved progress, pause and resume.

### 5. Diagnose Play Protect messages correctly

Different Android messages have different causes:

- **App scan recommended / unknown to Play Protect:** the service has not seen the app before. Allow the scan. Google's developer guidance says an appeal does not remove this informational prompt.
- **Harmful app blocked:** Play Protect has classified the APK as potentially harmful. Recheck the final APK, dependencies, permissions, build integrity and privacy behaviour. If the review finds no policy violation, submit the APK for Google's official Play Protect appeal.
- **Unverified developer:** this is Android developer identity verification, not the same as a malware classification. Plan to register the package and signing identity through the Android Developer Console ahead of the wider 2027 rollout. The initial September 2026 enforcement in Brazil applies to listed participating app stores; direct APK sideloading is not covered by that initial phase.

Keep Play Protect enabled. Do not instruct users to turn off security scanning or bypass a harmful-app block.

## Release checklist

- [ ] Confirm the exact package name and version from the built APK.
- [ ] Confirm the release signing certificate and preserve its backup.
- [ ] Review and minimize the merged permission list.
- [ ] Verify APK alignment, signature and archive integrity.
- [ ] Publish the APK SHA-256 hash and source commit.
- [ ] Complete clean-install and update tests on the Galaxy A05.
- [ ] Run Play Protect's scan and resolve any genuine detection before release.
- [ ] Use Google's official appeal process if a harmful classification is demonstrably incorrect.
- [ ] Prepare Android Developer Console registration for future direct-distribution requirements.

## Official Android guidance

- [Google Play Protect warning guidance](https://developers.google.com/android/play-protect/warning-dev-guidance)
- [Android app signing](https://developer.android.com/studio/publish/app-signing)
- [Android developer verification FAQ](https://developer.android.com/developer-verification/guides/faq)
