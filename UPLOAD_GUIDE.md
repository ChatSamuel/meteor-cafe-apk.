# Repository Update Guide

This repository can be updated in place. The process does not create a second repository or change its visibility.

## Apply a documentation package

1. Upload the documentation archive to the repository root.
2. Commit the uploaded file to the `main` branch.
3. Open **Actions** and select the documentation update workflow.
4. Run the workflow on `main` and review the resulting commit.

The workflow applies the README, notices, project documentation and Android adaptation notes while preserving the Android source, original game JAR, artwork, screenshots and build workflow.

## Build the Android application

1. Open **Actions**.
2. Select the Meteor Cafe Android build workflow.
3. Run the workflow on `main` or use the latest successful build.
4. Download the `METEOR-CAFE-1.1.0-ANDROID` artifact.

Before publishing a public APK release, complete the signing and security steps in `docs/ANDROID_SECURITY_AND_TRUST.md`.
