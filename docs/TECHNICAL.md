# Technical overview

## Runtime architecture

Meteor Cafe is a Java ME MIDlet. The Android application embeds the J2ME-Loader runtime and starts the original MIDlet through a dedicated launcher.

## Display model

- Original MIDP canvas: **240 × 320**.
- Logical game area observed in the JAR: **240 × 268**, centered in the canvas.
- Reference Android device: Samsung Galaxy A05, **720 × 1600** display.
- Version 1.1.0 uses portrait orientation and preserves the original aspect ratio.
- The unused outer display area is filled with a softened visual background; the game sprites and logical play area are not stretched.

## Touch controls

The original game handles pointer press, release and drag events. In Android version 1.1.0, the four coloured order buttons and their corresponding touch targets were shifted down by 8 logical pixels. Keeping the visual elements and hitboxes aligned is essential to preserving accurate input.

## Game archives and hashes

| File | SHA-256 |
|---|---|
| Untouched original JAR | `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6` |
| JAR integrated in Android version 1.1.0 | `81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a` |

The integrated JAR contains the documented button-position adjustment. The separate reference copy remains unchanged.

## Known scope

Version 1.1.0 is a portrait-oriented build. Landscape support and broad compatibility across other Android devices have not been established. See [Project status](STATUS.md) for the current device-test summary.
