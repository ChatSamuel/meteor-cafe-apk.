# Meteor Cafe — Samsung Galaxy A05 adaptation

**Reference device:** Samsung Galaxy A05, 720 × 1600 display, portrait orientation.  
**Application ID:** `com.apk.engineering.meteorcafe`  
**Application title:** `Meteor Cafe`  
**Android version:** 1.1.0 (`versionCode 2`)

## Display layout

The game JAR uses a 240 × 320 MIDP canvas and a 240 × 268 logical game area. Version 1.1.0 keeps those original dimensions and uses proportional scaling with centered alignment.

Changing the logical canvas to 240 × 533 would alter the virtual touch coordinates without expanding the actual game scene. Filling the entire display by stretching would distort characters, tables, text and the four coloured order buttons.

The Android build instead draws a softened background extension behind the clear original viewport. This fills the unused physical display area while preserving the original game image and its proportions.

- Virtual canvas: 240 × 320.
- Logical game area: 240 × 268.
- Orientation: portrait.
- Scaling: fit, preserving aspect ratio.
- Alignment: centered.
- Outer background: softened image derived from the game.

The additional background does not increase the logical gameplay area. A true increase in playable height would require coordinated changes to scenes, HUD elements, animations and touch areas.

## Four coloured order buttons

The vertical reference used to position the four-button row was adjusted in the game bytecode:

- Previous value: `268`.
- Updated value: `276`.
- Offset: +8 logical pixels.

The same routine supplies the coordinates used to register the four touch areas, so the visual buttons and their hitboxes move together. Button colours and order-selection rules were not changed.

The spacing remains an area for further visual review during gameplay, particularly in scenes where the buttons appear close to tables or other elements.

## Application identity

- Android application title: `Meteor Cafe`.
- Original logo used for the app icon.
- Application ID: `com.apk.engineering.meteorcafe`.
- The package retains the application identifier used by the dedicated project.

## JAR preservation and integrity

Untouched original JAR:

`METEOR_CAFE_APK_DEDICADO/original/meteor_cafe_original.jar`

SHA-256:

`a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`

JAR integrated in Android version 1.1.0:

SHA-256:

`81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a`

The integrated JAR contains the documented button-position adjustment. The separate original reference copy remains unchanged.

## Main Android files

- `app/src/main/java/javax/microedition/lcdui/Canvas.java` — background extension behind the original viewport.
- `app/src/main/java/ru/playsoftware/j2meloader/MeteorCafeLauncherActivity.java` — dedicated launch configuration and JAR integrity check.
- `app/build.gradle` — application metadata and the dedicated Android variant.
- `app/src/meteor/AndroidManifest.xml` — application title and icon resources.
- `app/src/meteor/res/mipmap-*` — launcher icons derived from the original logo.
- `app/src/main/assets/games/meteor_cafe.jar` — integrated game JAR.
- `original/meteor_cafe_original.jar` — untouched reference copy.

## Device test checklist

- [x] Application installed and launched on the Samsung Galaxy A05.
- [x] Application title and original logo displayed.
- [x] Game centered in portrait orientation.
- [x] Original aspect ratio retained.
- [ ] Review button spacing across all gameplay scenes.
- [ ] Verify audio and MIDI playback across a complete session.
- [ ] Verify saved progress, pause and resume.
- [ ] Test on additional Android devices.
