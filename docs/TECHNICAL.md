# Notas técnicas

## Arquitetura

Meteor Cafe é um MIDlet Java ME. A aplicação Android usa o runtime do J2ME-Loader incorporado no projeto dedicado para carregar o JAR do jogo diretamente.

## Tela e coordenadas

- Canvas de referência do jogo: 240 × 320.
- Área lógica observada no JAR: 240 × 268, centralizada no Canvas.
- O Android escala mantendo a proporção. O fundo externo é preenchido visualmente para reduzir faixas vazias sem alongar os sprites.
- A base estável usa orientação retrato. Landscape não deve ser presumido suportado.

## Controles

O jogo implementa eventos de ponteiro. Na base 1.1.0, os quatro botões coloridos e seus alvos de toque receberam deslocamento vertical de +8 px lógicos. A mudança foi feita em conjunto para manter desenho e hitbox alinhados.

## JARs e integridade

- JAR de jogo integrado na base 1.1.0: SHA-256 `81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a`.
- JAR original preservado: SHA-256 `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`.

O segundo hash é uma cópia de referência sem a alteração de posicionamento dos botões.
