# Meteor Cafe — dedicated Android application

This project integrates the J2ME-Loader Android runtime and launches the **MeteorCafe** MIDlet directly. The Android application version documented for this repository is **1.1.0** (`versionCode 2`), tested on a Samsung Galaxy A05.

## Application identity

- Android application title: **Meteor Cafe**.
- Application ID: `com.apk.engineering.meteorcafe`.
- Original MIDlet: MeteorCafe 1.18.0 (MIDP 2.0 / CLDC 1.1).
- Launcher icon: derived from the original game logo and retained in the `meteor` flavor resources.

## Launch sequence

1. Android opens `MeteorCafeLauncherActivity`.
2. The launcher copies the bundled JAR and verifies its SHA-256 value.
3. The runtime prepares the MIDlet classes.
4. The MIDlet descriptor is read from the JAR manifest.
5. The dedicated 240 × 320 portrait profile is applied.
6. The runtime opens the Meteor Cafe MIDlet directly.

## Display adaptation

- Original canvas: 240 × 320.
- Logical game area identified in the JAR: 240 × 268.
- On the Samsung Galaxy A05, the original game view is centered and the unused outer display area is filled with a softened background derived from the game.
- Sprites and the logical play area retain their original proportions.
- The four coloured order buttons and their touch targets use the documented +8 logical-pixel vertical adjustment.
- The reference Android version uses portrait orientation.

## Build

The configured build environment uses JDK 21, Android SDK Platform 37, Build Tools 37.x and NDK `29.0.14206865`. Restore the TinySoundFont submodule before a local build when it is not present in the checkout.

```bash
./gradlew :app:assembleMeteorDebug
```

The repository's GitHub Actions workflow automates tool installation, submodule restoration, JAR integrity checks and APK generation.

## JAR integrity

- Integrated JAR: `app/src/main/assets/games/meteor_cafe.jar`.
- Integrated JAR SHA-256: `81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a`.
- Untouched original JAR: `original/meteor_cafe_original.jar`.
- Original JAR SHA-256: `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`.

The integrated JAR contains the documented button-position change. The separately stored original JAR is unchanged.

## Licences and credits

J2ME-Loader is distributed under Apache License 2.0. TinySoundFont is distributed under MIT. The original Meteor Cafe game credits 3 Dynamics (Asia) Limited as developer and producer and THQ as distributor. See the repository's `NOTICE.md` and `docs/CREDITS_AND_RIGHTS.md` for attribution and corporate history.
