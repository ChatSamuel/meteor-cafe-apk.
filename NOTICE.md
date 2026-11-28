# Notices and third-party components

## Meteor Cafe

The original **Meteor Cafe** game is credited in its in-game credits to **3 Dynamics (Asia) Limited** as developer and producer, and **THQ** as distributor. The game credits display © 2010. The original Java ME JAR, logo, characters, graphics, music, sound effects and other game assets are part of the original work.

This repository documents an independent preservation and Android compatibility project. It does not claim authorship of the original game or grant a licence to the game's original assets. The historical corporate acquisition of 3 Dynamics is documented in [Credits and rights](docs/CREDITS_AND_RIGHTS.md); the available public records do not establish whether copyright in Meteor Cafe was separately assigned or who currently controls every relevant right.

## J2ME-Loader

The Android application incorporates the J2ME-Loader runtime. Its upstream project is available at <https://github.com/nikita36078/J2ME-Loader>. The runtime is distributed under the Apache License, Version 2.0. The full licence text and upstream notices are retained in `android/METEOR_CAFE_APK_DEDICADO/vendor/J2ME-Loader/LICENSE` and `THIRD_PARTY_NOTICES.md`.

## TinySoundFont

The J2ME-Loader project references TinySoundFont as a Git submodule. The build workflow restores it from <https://github.com/schellingb/TinySoundFont>. TinySoundFont is distributed under the MIT License; its licence and copyright notice must accompany the restored source.

## Repository licensing

No single open-source licence is applied to every item in this repository. The J2ME-Loader runtime, TinySoundFont, the Android adaptation and the original Meteor Cafe materials have separate origins and licensing terms. The Apache-2.0 licence for J2ME-Loader does not extend to Meteor Cafe or its original assets.
