# Meteor Café — Auditoria técnica inicial

**Arquivo analisado:** `meteor_cafe_touch_240x320_152234.jar`  
**Data da análise:** 15/11/2026  
**Tipo de análise:** inspeção estática do pacote, manifesto, bytecode e recursos. O jogo ainda não foi executado num emulador ou no aparelho físico.

## 1. Integridade e identificação

- Tamanho: **1.142.287 bytes**.
- Entradas ZIP: **95**.
- Verificação CRC: **aprovada** (`testzip()` não encontrou entrada com CRC inválido).
- SHA-256: `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`.
- Classes Java: **67**.
- Pacotes de recursos proprietários `RP*.rp`: **24**.
- Não foi encontrado arquivo JAD dentro do JAR.

### Manifesto observado

| Campo | Valor |
|---|---|
| MIDlet-Name | MeteorCafe |
| MIDlet-Version | 1.18.0 |
| MIDlet-Vendor | THQ |
| Classe MIDlet | `main.MeteorCafeMIDlet` |
| Perfil | MIDP 2.0 |
| Configuração | CLDC 1.1 |
| Criador indicado | Metaflow |
| `MIDlet-Touch-Support` | `true` |
| `MIDlet-Scaleup-Support` | `false` |
| `MIDlet-Data-Size` | 16.384 bytes |

O manifesto declara THQ como fabricante/publicador, mas o JAR não possui assinatura digital. Um arquivo auxiliar `supplied by kriker.ini` contém referência a um fórum de terceiros. Portanto, a procedência exata deste exemplar não está autenticada: os metadados identificam o jogo, mas não provam que o binário seja idêntico ao pacote oficial distribuído pela THQ. Isso não é, por si só, evidência de malware.

## 2. Descoberta de resolução lógica

O nome do arquivo indica 240×320, mas a inspeção do bytecode encontrou uma área lógica de jogo de **240×268**. O código centraliza essa área na tela disponível e habilita o modo fullscreen do Canvas.

Essa é a referência lógica que devemos preservar inicialmente. Não devemos redesenhar o jogo em 720×1600 nem esticar a imagem para preencher a tela do Galaxy A05. Primeiro devemos testar o jogo e observar como a versão original apresenta as áreas fora da viewport.

## 3. APIs e funcionamento identificados

- O MIDlet principal cria um `GameCanvas` e executa um ciclo de jogo numa thread.
- Há handlers de toque (`pointerPressed`, `pointerReleased` e `pointerDragged`) além de handlers de teclado.
- As referências de APIs encontradas incluem `javax.microedition.lcdui`, `javax.microedition.lcdui.game`, `javax.microedition.media`, `javax.microedition.rms` e `javax.microedition.midlet`.
- Não foram encontradas referências diretas óbvias a pacotes proprietários `com.nokia.*` nas constantes de classe inspecionadas. Isso melhora a perspectiva de compatibilidade com um runtime J2ME genérico, mas não garante funcionamento.
- O jogo utiliza RMS (`RecordStore`) para persistência local.
- O áudio passa pela MMAPI (`Manager.createPlayer`, `realize`, `prefetch`, `setLoopCount`, `VolumeControl` e `start`). Os recursos MIDI são tocados pelo caminho `audio/midi` identificado no código.

## 4. Inventário de recursos extraídos

Foram decodificados **759 registros** dos 24 pacotes `RP*.rp`:

| Tipo | Quantidade | Interpretação atual |
|---|---:|---|
| PNG (`0xFD`) | 248 | Imagens e spritesheets; assinatura PNG validada |
| MIDI (`0x00`) | 19 | Música/áudio MIDI; cabeçalho MThd validado |
| Binário (`0xFE`) | 248 | Registros proprietários do motor; formato ainda em investigação |
| Binário (`0xF7`) | 239 | Registros proprietários do motor; finalidade exata ainda em investigação |
| Texto (`0xFA`) | 5 | Tabelas de texto/localização |

Os recursos incluem ilustrações, fundos, telas de história, elementos de interface, imagens de personagens e outros elementos gráficos. Os registros `0xFE` e `0xF7` ainda não foram totalmente interpretados; não devem ser tratados como formatos conhecidos até que a estrutura seja documentada.

## 5. O que está confirmado e o que falta

### Confirmado por inspeção estática

- O JAR passa na verificação CRC.
- O manifesto declara MIDP 2.0, CLDC 1.1 e suporte a toque.
- O código usa uma viewport lógica de 240×268, centralizada.
- O jogo contém 248 PNGs, 19 MIDIs e registros proprietários em pacotes RP.
- O código usa MMAPI para áudio e RMS para dados persistentes.

### Ainda não confirmado

- Se esta versão inicia corretamente no J2ME-Loader ou no Galaxy A05.
- Se o toque funciona de forma correta em todos os menus e fases.
- Se a música MIDI e os efeitos funcionam com o sintetizador usado no Android.
- O significado completo dos registros binários `0xFE` e `0xF7`.
- Se o pacote é byte a byte idêntico a uma distribuição oficial da THQ.
- O comportamento visual e a proporção de tela em execução real.

## 6. Próxima etapa recomendada

1. Executar uma cópia de teste do JAR no J2ME-Loader, sem modificar o original.
2. Registrar captura de tela/vídeo desde a inicialização até o primeiro trecho jogável.
3. Verificar toque, orientação, viewport, áudio e salvamento.
4. Descompilar as 67 classes com CFR ou Vineflower e documentar as estruturas dos recursos `RP`.
5. Só então decidir se basta empacotar o MIDlet com runtime compatível ou se alguma API exige adaptação.

## 7. Arquivos derivados desta análise

- `meteor_cafe_extracted_resources.zip`: recursos extraídos e índice dos registros.
- `meteor_cafe_assets_overview.jpg`: mosaico de visualização dos 248 PNGs.
- `meteor_cafe_largest_assets.jpg`: mosaico dos principais arquivos gráficos.

**Preservação:** o JAR original foi mantido intacto. Os arquivos derivados servem à análise técnica; direitos de redistribuição e publicação precisam ser tratados separadamente com os titulares correspondentes.
