# Building Meteor Cafe for Android

## Project location

The dedicated Android project is located at:

`android/METEOR_CAFE_APK_DEDICADO/vendor/J2ME-Loader`

## Build environment

The current GitHub Actions pipeline provisions:

- JDK 21;
- Android SDK Platform 37;
- Android Build Tools 37.x;
- Android NDK `29.0.14206865`;
- the TinySoundFont source dependency used for MIDI playback.

The pipeline restores the dependency, verifies the original and integrated game-JAR hashes, and runs the Gradle task configured for the dedicated Meteor Cafe variant.

## Local build

1. Install JDK 21 and Android Studio with the SDK and NDK components listed above.
2. Restore the TinySoundFont dependency if it is not present in the checkout.
3. Open the J2ME-Loader project directory in Android Studio, or run the configured Gradle task from that directory:

```bash
./gradlew :app:assembleMeteorDebug
```

## Continuous integration

The repository's `build-meteor.yml` workflow builds the Android application and publishes the APK as a GitHub Actions artifact named `METEOR-CAFE-1.1.0-ANDROID`.

The public release process will use a permanent project signing key and the release build variant. See [Android Security and Distribution](ANDROID_SECURITY_AND_TRUST.md) for the signing, validation and publication requirements.

## Integrity checks

The build checks both the game JAR integrated into the Android application and the untouched original JAR. Their SHA-256 values are documented in [Technical notes](TECHNICAL.md).
