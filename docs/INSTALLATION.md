# Installing Meteor Cafe on Android

## Download the APK

1. Open the [Meteor Cafe Android build workflow](https://github.com/ChatSamuel/meteor-cafe-apk/actions/workflows/build-meteor.yml).
2. Select the latest run marked **Success**.
3. In **Artifacts**, download `METEOR-CAFE-1.1.0-ANDROID`.
4. Extract the downloaded ZIP archive. The installer is `METEOR_CAFE_1.1.0.apk`.

## Install

1. Open the APK with the Android file manager.
2. When prompted, allow installation from that source.
3. Return to the installer and confirm.
4. Launch **Meteor Cafe** from the app list.

## Updating an existing installation

Android permits an in-place update only when the package identifier and signing certificate are compatible. An APK signed with a different development key may be rejected as a package-signature conflict. Before uninstalling an existing copy, consider whether it contains saved progress; uninstalling may remove local application data.

## Build artifacts

GitHub Actions artifacts are temporary and follow the retention period configured in the workflow. The workflow page shows the available builds and their expiration dates.
