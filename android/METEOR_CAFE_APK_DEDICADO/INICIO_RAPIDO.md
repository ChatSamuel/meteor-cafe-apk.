# METEOR CAFE — início rápido

**Versão de referência:** 1.1.0 (versionCode 2)

Este é o projeto Android dedicado selecionado como versão estável de referência. Ele incorpora o runtime J2ME-Loader e inicia diretamente o MIDlet MeteorCafe.

- **Nome exibido no Android:** `Meteor Cafe`
- **Application ID:** `com.apk.engineering.meteorcafe`
- **Variante:** `meteorDebug` (assinatura de desenvolvimento; o rótulo do aplicativo não contém “Debug”)
- **Jogo integrado:** MeteorCafe 1.18.0
- **Canvas MIDP:** 240 × 320, orientação retrato
- **Aparelho de referência testado:** Samsung Galaxy A05 (720 × 1600)

## Abrir e compilar

Abra `vendor/J2ME-Loader` no Android Studio ou use o workflow `Compilar METEOR CAFE 1.1.0` do repositório. Antes da compilação local, restaure o submódulo TinySoundFont conforme `vendor/J2ME-Loader/scripts/restore_tinysoundfont.sh` ou `.ps1`.

Com Java 21, Android SDK/API 37, NDK `29.0.14206865` e dependências disponíveis:

```bash
./gradlew :app:assembleMeteorDebug
```

O APK é gerado como artefato do GitHub Actions. Para instalação, consulte `docs/INSTALLATION.md` na raiz do repositório.
