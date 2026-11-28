# Installing Meteor Cafe on Android

## Download the APK

1. Open the repository's [Actions page](https://github.com/ChatSamuel/meteor-cafe-apk/actions).
2. Select **Compilar METEOR CAFE 1.1.0**.
3. Open the most recent run marked **Success**.
4. Under **Artifacts**, download **METEOR-CAFE-1.1.0-ANDROID**.
5. Extract the downloaded ZIP archive. The installer is `METEOR_CAFE_1.1.0.apk`.

If there is no successful run or the artifact has expired, use **Run workflow** on the build workflow. When the new run finishes successfully, download its artifact from the same page.

GitHub Actions artifacts are temporary and follow the retention period configured in the workflow.

## Install

1. Open the APK with the Android file manager.
2. When Android asks, allow installation from the app used to open the file.
3. Return to the installer and confirm the installation.
4. Launch **Meteor Cafe** from the app list.

## Updating an existing installation

Android permits an in-place update only when the package identifier and signing certificate are compatible. An APK signed with a different development key may be rejected as a package-signature conflict. Before uninstalling an existing copy, consider whether it contains saved progress; uninstalling may remove local application data.

## Build artifacts

The build artifact is published by GitHub Actions. Its availability and expiration date are shown on the corresponding workflow run.
