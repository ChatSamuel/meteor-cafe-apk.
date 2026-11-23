# METEOR CAFE — aplicativo Android dedicado

Este projeto integra o runtime Android do J2ME-Loader e inicia diretamente o MIDlet **MeteorCafe**. A base selecionada para o repositório é a versão **1.1.0** (versionCode 2), testada pelo usuário no Samsung Galaxy A05.

## Identidade do aplicativo

- Nome apresentado pelo Android: **Meteor Cafe**
- Application ID: `com.apk.engineering.meteorcafe`
- Variante de compilação: `meteorDebug`
- MIDlet original: MeteorCafe 1.18.0 (MIDP 2.0 / CLDC 1.1)
- Ícone: logotipo original preservado nos recursos do flavor `meteor`

A variante `meteorDebug` é o tipo de build usado no processo de testes; isso não altera o nome exibido ao usuário, que permanece `Meteor Cafe`. O APK exportado pelo workflow recebe o nome limpo `METEOR_CAFE_1.1.0.apk`.

## Inicialização

1. Android abre `MeteorCafeLauncherActivity`.
2. O launcher copia o JAR integrado e confere o SHA-256.
3. O conversor DEX usado pelo runtime prepara as classes do MIDlet.
4. O descriptor é lido do manifesto JAR.
5. Um perfil dedicado 240 × 320, retrato e com toque é criado/reaplicado.
6. O launcher abre `MicroActivity` diretamente com o Meteor Cafe.

## Adaptação visual da versão 1.1.0

- Canvas original: 240 × 320.
- Área lógica identificada no jogo: 240 × 268.
- No Galaxy A05 (720 × 1600), o conteúdo é centralizado e a área externa é preenchida com um fundo suavizado derivado do próprio jogo.
- Sprites e área jogável mantêm proporção; não se usa esticamento vertical.
- Os quatro botões coloridos tiveram deslocamento de +8 pixels lógicos, com os alvos de toque correspondentes.
- A orientação desta base está fixada em retrato.

## Compilação

Requisitos principais: JDK 21, Android SDK Platform 37 (canal de prévia utilizado pelo build), Build Tools 37.x e NDK `29.0.14206865`. Restaure TinySoundFont antes da compilação local, pois o ZIP de origem não inclui o conteúdo do submódulo Git.

```bash
./gradlew :app:assembleMeteorDebug
```

O workflow oficial do repositório automatiza a instalação das ferramentas, a restauração do TinySoundFont, as verificações SHA-256 e a publicação do APK como artifact do GitHub Actions.

## Integridade do JAR

- JAR integrado: `app/src/main/assets/games/meteor_cafe.jar`
- SHA-256 integrado: `81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a`
- JAR original preservado: `original/meteor_cafe_original.jar`
- SHA-256 original: `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`

O JAR integrado difere da cópia original apenas pelo ajuste documentado da fileira de botões. A cópia original permanece intacta.

## Licenças e créditos

O runtime J2ME-Loader é distribuído sob Apache License 2.0; consulte `LICENSE`. TinySoundFont é restaurado de seu repositório upstream e usa licença MIT. Meteor Cafe é conteúdo de terceiros: os créditos observados no JAR citam 3 Dynamics (Asia) Limited e THQ. Este repositório não declara titularidade sobre o jogo ou seu logotipo e não concede autorização adicional de distribuição. Consulte `NOTICE.md` e a documentação de direitos na raiz.
