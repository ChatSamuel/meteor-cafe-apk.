# Meteor Cafe — technical audit 02
## Static reverse engineering and Android compatibility

**File examined:** `meteor_cafe_touch_240x320_152234.jar`  
**Size:** 1,142,287 bytes  
**SHA-256:** `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`  
**Archive integrity:** ZIP CRC check passed  
**Scope:** static inspection of the JAR and comparison of its referenced APIs with the J2ME-Loader source included in the project.

This audit records static findings. API matching is useful for compatibility planning, but it is not a substitute for testing the game on an actual Android device.

---

## 1. Observed technical identity

| Field | Value |
|---|---|
| MIDlet-Name | MeteorCafe |
| MIDlet-Version | 1.18.0 |
| MIDlet-Vendor | THQ |
| Main MIDlet class | `main.MeteorCafeMIDlet` |
| Profile | MIDP 2.0 |
| Configuration | CLDC 1.1 |
| Touch support | `MIDlet-Touch-Support: true` |
| Scale-up support | `MIDlet-Scaleup-Support: false` |
| Declared data size | `MIDlet-Data-Size: 16384` |
| Created-By field | Metaflow |
| Java classes | 67 |
| Class-file version | Java 45.3 |

The JAR does not contain a JAD file. The touch-support declaration is consistent with calls to `pointerPressed`, `pointerReleased` and `pointerDragged` in the inspected code.

## 2. Architecture and execution loop

The main class, `main.MeteorCafeMIDlet`, creates the game layer. Class `bk`:

- extends `javax.microedition.lcdui.game.GameCanvas`;
- implements `Runnable`;
- requests full-screen mode;
- starts an execution thread;
- updates the game state;
- obtains a graphics context through `getGraphics()`;
- draws and presents frames through `flushGraphics()`.

The central rendering routine is called through the game layer as `ab.a(Graphics)`.

### Internal dimensions

The inspected code returns fixed dimensions of **240 × 268 pixels** for the logical game area. The canvas is requested in full-screen mode, and the logical area is centered within it:

- expected original canvas: 240 × 320;
- logical game area: 240 × 268;
- horizontal margin: 0 pixels;
- vertical margin: 26 pixels above and 26 pixels below.

The positioning routine calculates offsets from the difference between the canvas size and the logical game dimensions. Touch-event handling subtracts those offsets before testing interactive regions.

**Implication for the Galaxy A05:** the internal game model should remain 240 × 320, with the original 240 × 268 logical area. Android scaling should preserve the aspect ratio instead of changing the game's coordinate system or stretching sprites to fill a 720 × 1600 display.

## 3. Touch controls and keyboard input

The game implements three pointer callbacks:

- `pointerPressed(x, y)`;
- `pointerReleased(x, y)`;
- `pointerDragged(x, y)`.

These events feed an internal dispatcher that corrects coordinates for the game-area offset, checks interactive objects, tests rectangular touch regions and forwards the event to the selected element.

Class `ad` stores interaction rectangles with `x`, `y`, `width` and `height` values. Its hit-test method checks whether a point falls within those bounds.

The game also implements `keyPressed`, `keyReleased` and queries `GameCanvas.getKeyStates()`. The inspected build therefore supports keyboard input in addition to touch events.

## 4. Extracted resources

The extraction identified 759 records in internal `RP*.rp` resource packages:

| Resource type | Count | Interpretation |
|---|---:|---|
| PNG | 248 | Images and sprites |
| MIDI | 19 | MIDI music and/or audio |
| `0xFE` binary | 248 | Proprietary resource records not fully decoded |
| `0xF7` binary | 239 | Proprietary resource records not fully decoded |
| `0xFA` | 5 | Text and localization tables |

The PNG images were extracted and their dimensions inspected. The archive includes sprite sheets, illustrations and screens of different sizes. The proprietary binary groups are part of the game's resource format and should not be classified as corrupted files solely because they are not standard media formats.

### Text and languages

The five `0xFA` tables contain 136 positions each. The extracted language tables include English, French, German, Italian and Spanish. No Portuguese table was identified in the examined JAR.

### Gameplay information found in the text resources

The original strings describe a café-management arcade game. They refer to starting a shift, waiting for customers to sit, matching the coloured order button, throwing food onto plates, improving customer mood, earning tips, completing orders before the shift timer expires, purchasing upgrades and unlocking additional restaurant content.

The stage or location names found in the strings include Street, Alps, GearCity, HongKong, BandShow, BattleField, UnderWorld and WhiteHouse.

## 5. In-game credits

The English credit strings in the examined game include:

- © 2010;
- developed and produced by **3 Dynamics (Asia) Limited**;
- distributed by **THQ**;
- historical support address: `support@3dynamics.com`.

These are credits found inside the examined game file. They do not establish whether the historical support address is still active or identify the current owner of the game's copyright. The corporate acquisition history is documented separately in `docs/CREDITS_AND_RIGHTS.md`.

## 6. APIs referenced by the JAR

| API | Observed use |
|---|---|
| `MIDlet` | Application lifecycle |
| `Display` / `Displayable` | Display handling and dimensions |
| `Canvas` | Full-screen display and pointer events |
| `GameCanvas` | Graphics buffer and key-state handling |
| `Graphics` | 2D drawing, clipping, translation and pixels |
| `Image` | Image loading and pixel access |
| `Font` | Font metrics and text rendering |
| `Command` | MIDP command creation |
| MMAPI `Manager` / `Player` | Media playback |
| `VolumeControl` | Volume adjustment |
| RMS `RecordStore` | Persistent storage |

No references to proprietary `com.nokia.*` namespaces were found in the inspected external-reference set. This reduces the apparent need for manufacturer-specific APIs, but does not replace runtime testing.

## 7. Static compatibility with J2ME-Loader

The J2ME-Loader source included in the project contains implementations corresponding to the APIs used by Meteor Cafe.

| Meteor Cafe requirement | J2ME-Loader implementation | Static assessment |
|---|---|---|
| `GameCanvas` | Implemented | API coverage found |
| Pointer callbacks | Implemented in `Canvas` | API coverage found |
| Virtual touch-coordinate scaling | Conversion to virtual display coordinates | Positive indication |
| `Command` | MIDP constructor implemented | API coverage found |
| `Graphics` | Inspected methods present | API coverage found |
| `Image` | `createImage` and `getRGB` present | API coverage found |
| MMAPI `createPlayer(InputStream, String)` | Implemented | Positive indication |
| MIDI media types | `audio/midi` and `audio/x-midi` accepted by Manager | Audio still requires device testing |
| `Player` / `VolumeControl` | Interfaces and implementations present | Positive indication |
| RMS `RecordStore` | Implemented | API coverage found |
| Resources inside the JAR | `AppClassLoader` reads packaged resources | Positive indication |

The inspected J2ME-Loader profile uses a 240 × 320 virtual display, preserves the aspect ratio and enables touch input by default. Its `Canvas` implementation maps Android touches to virtual MIDP coordinates and dispatches the corresponding events.

**Static conclusion:** J2ME-Loader is a strong candidate for running the original Meteor Cafe JAR without rewriting the game's logic. The inspected APIs in the game and runtime show substantial overlap.

**Limit:** API coverage alone does not prove identical behaviour. Startup, audio, saving and full gameplay must be checked on the target Android device.

## 8. Building J2ME-Loader from source

The J2ME-Loader project declares `app/src/main/cpp/mmapi_tsf/TinySoundFont/` as a Git submodule. A source ZIP downloaded without submodules may contain an empty directory at that path. Restore the submodule from:

`https://github.com/schellingb/TinySoundFont.git`

The repository build workflow restores the dependency before compiling. The presence of a sample `midlet` product flavor in J2ME-Loader should not be confused with an automatic one-click conversion of any arbitrary JAR into an Android app.

## 9. Preservation strategy

1. Keep an untouched copy of the original JAR and record its SHA-256.
2. Prefer runtime compatibility fixes over rewriting original game logic.
3. Record any visual or behavioural differences found during device testing.
4. Keep touch hitboxes aligned with their visual controls.
5. Retest launch, menus, gameplay, audio and saved progress after each change.
6. Keep the dedicated Android launcher separate from the original game's source assets.

---

## Conclusion

The examined JAR is structurally intact, includes touch-event handling and references a set of common Java ME APIs that are represented in the J2ME-Loader source. Retaining the original game and using a compatibility runtime provides a practical preservation path while keeping the original game logic and presentation as the reference.
