# Meteor Cafe — initial technical audit

**File examined:** `meteor_cafe_touch_240x320_152234.jar`  
**Analysis date:** 15 November 2026  
**Method:** static inspection of the archive, manifest, bytecode and embedded resources.

## Integrity and identification

- File size: **1,142,287 bytes**.
- ZIP entries: **95**.
- CRC integrity check: **passed**.
- SHA-256: `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`.
- Java classes: **67**.
- Proprietary resource packages (`RP*.rp`): **24**.
- No JAD file was found inside the JAR.

### Manifest values

| Field | Observed value |
|---|---|
| MIDlet-Name | MeteorCafe |
| MIDlet-Version | 1.18.0 |
| MIDlet-Vendor | THQ |
| MIDlet class | `main.MeteorCafeMIDlet` |
| Profile | MIDP 2.0 |
| Configuration | CLDC 1.1 |
| Created-By | Metaflow |
| MIDlet-Touch-Support | `true` |
| MIDlet-Scaleup-Support | `false` |
| MIDlet-Data-Size | 16,384 bytes |

The manifest identifies THQ as the vendor. The JAR is not digitally signed. An auxiliary file named `supplied by kriker.ini` contains a reference to a third-party forum; that file alone does not authenticate the provenance of this particular copy.

## Code structure

The main MIDlet class is `main.MeteorCafeMIDlet`. The inspected game code uses `GameCanvas`, a dedicated game loop and Java ME graphics and input APIs. The game declares touch support and contains pointer-event handlers.

## Embedded resources

The JAR contains proprietary resource packages, including PNG images, MIDI data and binary resource records. These resources are part of the original game format; binary records should not be treated as corrupted files merely because they are not ordinary images or audio files.

## Scope and limitations

This document records static findings from the inspected JAR. It does not independently establish the legal rights holder, authenticate the history of every copy of the game or prove compatibility with every Android device. Runtime behaviour is covered by the separate device-test records.
