# Compilação

## Base

- Fonte: `android/METEOR_CAFE_APK_DEDICADO/vendor/J2ME-Loader`.
- Variante Gradle: `:app:assembleMeteorDebug`.
- Java: 21.
- Android SDK: API 37.x (catálogo Preview consultado pelo workflow).
- Build Tools: versão 37.x estável disponível no catálogo.
- NDK: `29.0.14206865`.
- Dependência de áudio: TinySoundFont, restaurada no workflow porque o snapshot-fonte não traz o submódulo Git inicializado.

## Executar pelo GitHub Actions

1. Abra a aba **Actions**.
2. Selecione **Compilar METEOR CAFE 1.1.0**.
3. Use **Run workflow** na branch `main`.
4. Aguarde o job terminar com **Success**.
5. Baixe o artifact `METEOR-CAFE-1.1.0-ANDROID`.

O workflow verifica os SHA-256 do JAR modificado integrado ao app e do JAR original preservado. O APK resultante é assinado para desenvolvimento; não é uma assinatura de distribuição de loja. Uma assinatura de release deverá ser criada e protegida separadamente, nunca commitada neste repositório.
