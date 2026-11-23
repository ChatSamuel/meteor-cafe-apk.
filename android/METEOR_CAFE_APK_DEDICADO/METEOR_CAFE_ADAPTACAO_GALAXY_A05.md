# Meteor Cafe — adaptação para Samsung Galaxy A05
## Pacote de correção da versão que já compilou

**Alvo:** Samsung Galaxy A05, tela física 720 × 1600 px, orientação retrato.  
**Base:** código-fonte J2ME-Loader + launcher dedicado do Meteor Cafe.  
**Aplicativo:** `com.apk.engineering.meteorcafe`  
**Nome de exibição:** `Meteor Cafe`

## 1. Decisão de tela

O JAR do jogo utiliza Canvas MIDP de 240 × 320 e uma área lógica de jogo de 240 × 268. Por isso, esta versão mantém o Canvas em **240 × 320** e utiliza o modo de escala Fit com centralização e tela cheia.

Não mudamos o Canvas para 240 × 533: isso altera a região virtual de toque sem aumentar de verdade o cenário lógico do jogo. Forçar Fill também distorceria personagens, mesas, textos e os quatro botões coloridos.

### Como a tela será preenchida

A versão atual desenha, por trás da área original nítida, uma extensão de fundo desfocada criada a partir do primeiro quadro válido do próprio jogo e mantida como fundo durante a sessão. Assim, as extremidades físicas do Galaxy A05 deixam de ficar pretas, enquanto a imagem jogável continua com proporção e coordenadas originais.

- Canvas virtual mantido: 240 × 320
- Área lógica do jogo: 240 × 268
- Orientação: retrato
- Escala: Fit, proporção preservada
- Alinhamento: centralizado
- Tela cheia: ativada
- Fundo de preenchimento: primeiro quadro válido do jogo, reduzido e suavizado
- Renderização: Android Hardware Canvas quando API 26 ou superior; modo software como alternativa em versões mais antigas

**Limite conhecido:** o cenário jogável nítido não passa a ter 533 pixels de altura lógica. O espaço extra é preenchido com uma extensão visual desfocada. Uma reestruturação verdadeira de todas as telas para aumentar a área de jogo exigiria redesenhar cenários, HUD, animações e hitboxes; não é seguro conseguir isso apenas mudando a resolução.

## 2. Separação dos quatro botões coloridos

Foi feita uma alteração controlada no bytecode de `a.class`, método `c()`, instrução `sipush` que fornece a referência vertical da fileira de quatro botões.

- Valor anterior: 268
- Novo valor: 276
- Deslocamento: +8 pixels lógicos (aproximadamente 24 pixels físicos a 3×)

A sequência seguinte do mesmo método usa as coordenadas dos sprites para registrar as quatro áreas de toque na classe `z`. Portanto, o ajuste de posição dos sprites também move os retângulos de toque correspondentes. Não foram alteradas as quatro cores nem as regras dos pedidos.

**É um primeiro ajuste de espaçamento, que ainda precisa ser testado no aparelho** para confirmar que a fileira não encosta na barra de pedido/progresso em todas as telas.

## 3. Nome e ícone do aplicativo

- Nome do launcher: `Meteor Cafe`
- Corrigido o valor de debug `JL-Debug`, que era aplicado indevidamente ao flavor `meteor`.
- Ícone Android criado a partir do logotipo original extraído do recurso `RP3/res007_typefd.png` do JAR.
- Incluídos ícones legados por densidade e ícones adaptativos Android com camadas de fundo, primeiro plano e monocromática.
- Mantido o applicationId do projeto para permitir uma atualização da instalação existente quando a assinatura Android for compatível.

## 4. Preservação do JAR

O JAR original intacto está guardado em:

`METEOR_CAFE_APK_DEDICADO/original/meteor_cafe_original.jar`

SHA-256 original:
`a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`

O JAR incorporado ao APK tem apenas o ajuste documentado da fileira de botões.

SHA-256 do JAR incorporado:
`81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a`

O launcher valida esse SHA-256 antes de iniciar o MIDlet. A configuração do perfil é reaplicada a cada início para evitar que um perfil antigo preserve a escala/alinhamento anteriores.

## 5. Arquivos principais alterados

- `app/src/main/java/javax/microedition/lcdui/Canvas.java`
  - Fundo dinâmico desfocado atrás do viewport nítido, somente no flavor `meteor`.
- `app/src/main/java/ru/playsoftware/j2meloader/MeteorCafeLauncherActivity.java`
  - SHA-256 atualizado, perfil dedicado centralizado e modo de renderização adaptado.
- `app/build.gradle`
  - Nome `Meteor Cafe`, versão do aplicativo 1.1.0 e exclusão do flavor meteor da substituição `JL-Debug`.
- `app/src/meteor/AndroidManifest.xml`
  - Nome e ícones do aplicativo dedicados.
- `app/src/meteor/res/mipmap-*`
  - Ícones derivados do logotipo original.
- `app/src/main/assets/games/meteor_cafe.jar`
  - JAR com o deslocamento documentado de 8 pixels na fileira de botões.
- `original/meteor_cafe_original.jar`
  - Cópia intocada de segurança.

## 6. Compilação

O arquivo `build-meteor-v6.yml` deve substituir o conteúdo do workflow existente em:

`.github/workflows/build-meteor.yml`

O workflow verifica:
1. extração do projeto;
2. Java 21;
3. Android SDK/API 37, Build Tools e NDK;
4. SHA-256 do JAR modificado;
5. SHA-256 da cópia original preservada;
6. dependência TinySoundFont;
7. compilação da variante `MeteorDebug`;
8. existência do APK antes de publicar o artefato.

## 7. Testes necessários no Galaxy A05

- [ ] Instalação/atualização do APK
- [ ] Nome exibido como Meteor Cafe
- [ ] Ícone original no launcher
- [ ] Tela inteira sem faixas pretas
- [ ] Proporção dos sprites preservada
- [ ] Título, menus e gameplay
- [ ] Quatro cores: azul, vermelho, amarelo e verde
- [ ] Toque e seleção dos pedidos
- [ ] Separação visual entre clientes/mesas e botões
- [ ] Barra de pedido/progresso sem sobreposição
- [ ] Música e efeitos MIDI
- [ ] Pausa, retomada e salvamento de progresso

## 8. Estado

Esta é uma correção de código-fonte preparada para uma nova compilação. O projeto ainda precisa passar pelo GitHub Actions e ser instalado/testado no Galaxy A05. O fato de a versão anterior ter compilado não comprova antecipadamente que todas estas alterações novas também compilarão ou terão o resultado visual esperado.
