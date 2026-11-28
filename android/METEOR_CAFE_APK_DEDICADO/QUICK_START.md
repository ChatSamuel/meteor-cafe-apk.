# Meteor Cafe — quick start

**Reference Android version:** 1.1.0 (`versionCode 2`)

This is the dedicated Android project built around the J2ME-Loader runtime. It launches the original `MeteorCafe` MIDlet directly.

- **Application title:** `Meteor Cafe`
- **Application ID:** `com.apk.engineering.meteorcafe`
- **Integrated game:** MeteorCafe 1.18.0
- **MIDP canvas:** 240 × 320, portrait
- **Reference device:** Samsung Galaxy A05 (720 × 1600)

## Open and build

Open `vendor/J2ME-Loader` in Android Studio, or use the repository's **Compilar METEOR CAFE 1.1.0** GitHub Actions workflow. Before building locally, restore the TinySoundFont submodule using `vendor/J2ME-Loader/scripts/restore_tinysoundfont.sh` or `.ps1`.

With JDK 21, Android SDK Platform 37, Build Tools 37.x, NDK `29.0.14206865` and the required dependencies installed, run:

```bash
./gradlew :app:assembleMeteorDebug
```

For installation instructions, see `docs/INSTALLATION.md` at the repository root.
