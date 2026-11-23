# METEOR CAFE — status do projeto

**Versão de referência selecionada: 1.1.0 (versionCode 2).**

## Confirmado nesta versão

- [x] Aplicativo Android dedicado com runtime J2ME-Loader incorporado.
- [x] Nome de exibição `Meteor Cafe` (sem “Debug” ou “Teste”).
- [x] Ícone Android baseado no logotipo original do jogo preservado.
- [x] JAR integrado com SHA-256 conferido pelo launcher.
- [x] Cópia original do JAR preservada em `original/meteor_cafe_original.jar`.
- [x] Canvas original 240 × 320; proporção preservada.
- [x] Fundo externo suavizado para preencher a tela do Galaxy A05 sem esticar a arte jogável.
- [x] Ajuste vertical de +8 pixels lógicos nos quatro botões coloridos, acompanhado pelas áreas de toque.
- [x] Build Android executado com sucesso e jogo iniciado no Samsung Galaxy A05, conforme testes do usuário.

## Limitações conhecidas

- A versão de referência é retrato. O modo horizontal não faz parte desta versão estável.
- A fileira de botões coloridos ainda pode parecer próxima das mesas/clientes; o ajuste de +8 px melhorou o espaçamento, mas o refinamento permanece aberto.
- A compatibilidade em outros aparelhos ainda não foi verificada de forma ampla.
- A reprodução de áudio e a persistência devem continuar sendo testadas em novos aparelhos/builds.
- Uma atualização instalada por cima de outro APK de desenvolvimento pode falhar se a assinatura Android for diferente.

## Próximas etapas

1. Consolidar a documentação e os arquivos do repositório.
2. Refazer testes de toque, áudio, pausa e salvamento.
3. Corrigir o espaçamento dos botões sem alterar as mecânicas.
4. Estudar uma verdadeira adaptação horizontal em uma linha experimental separada, sem substituir esta base estável.
5. Identificar o atual titular dos direitos e solicitar autorização antes de uma distribuição pública do APK.
