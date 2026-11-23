# METEOR CAFÉ — AUDITORIA TÉCNICA 02
## Engenharia reversa estática e compatibilidade Android

**Arquivo analisado:** `meteor_cafe_touch_240x320_152234.jar`  
**Tamanho:** 1.142.287 bytes  
**SHA-256:** `a621bf0e11046aabb4ddcaa9c0c3d3a3aefa9340a415c2ce690ef977a394f8c6`  
**Resultado de integridade:** teste CRC do ZIP aprovado  
**Escopo:** análise estática do JAR e comparação das APIs utilizadas com o código-fonte J2ME-Loader enviado no pacote de conhecimento.

> Esta auditoria não afirma que o jogo já foi executado no Galaxy A05. As conclusões de compatibilidade abaixo são verificações estáticas de APIs e código-fonte; a execução real ainda precisa ser validada.

---

## 1. Identidade técnica observada

| Campo | Valor |
|---|---|
| MIDlet-Name | MeteorCafe |
| MIDlet-Version | 1.18.0 |
| MIDlet-Vendor | THQ |
| Classe principal | `main.MeteorCafeMIDlet` |
| Perfil | MIDP 2.0 |
| Configuração | CLDC 1.1 |
| Touch | `MIDlet-Touch-Support: true` |
| Scaleup | `MIDlet-Scaleup-Support: false` |
| Data declarada | `MIDlet-Data-Size: 16384` |
| Criador indicado | Metaflow |
| Arquivos de classe | 67 classes |
| Versão do bytecode | Java class-file 45.3 |

O JAR não contém um arquivo JAD. A declaração de suporte a toque no manifesto é corroborada por chamadas reais a `pointerPressed`, `pointerReleased` e `pointerDragged` no código.

## 2. Arquitetura e ciclo de execução

A classe principal `main.MeteorCafeMIDlet` cria a camada de jogo. A classe `bk`:

- estende `javax.microedition.lcdui.game.GameCanvas`;
- implementa `Runnable`;
- solicita modo de tela cheia;
- inicia uma thread de execução;
- atualiza a lógica do jogo;
- obtém o contexto gráfico com `getGraphics()`;
- desenha e publica cada quadro com `flushGraphics()`.

O motor central de renderização é chamado pela camada de jogo (`ab.a(Graphics)`).

### Dimensões internas

O código retorna dimensões fixas de **240 × 268 px** para a área lógica de jogo. O Canvas é solicitado em tela cheia. A área lógica é posicionada ao centro do Canvas:

- Canvas original esperado: 240 × 320;
- área lógica: 240 × 268;
- margem horizontal em 240 px: 0 px;
- margem vertical em 320 px: 26 px acima e 26 px abaixo.

A rotina de posicionamento calcula os deslocamentos a partir das diferenças entre o tamanho do Canvas e as dimensões lógicas. O sistema de eventos de toque desconta esses deslocamentos antes de verificar os alvos interativos.

**Implicação para o Galaxy A05:** a lógica interna deve continuar em 240 × 320 (Canvas) com a área útil original de 240 × 268. O Android deve escalar a imagem mantendo a proporção, sem alterar as coordenadas do jogo nem esticar os sprites para preencher 720 × 1600.

## 3. Controles: toque real e teclado

O jogo possui três callbacks de toque:

- `pointerPressed(x,y)`;
- `pointerReleased(x,y)`;
- `pointerDragged(x,y)`.

Esses eventos alimentam um despachante interno que:

1. corrige as coordenadas pelo deslocamento da área de jogo;
2. consulta objetos interativos;
3. verifica se o ponto está dentro de retângulos de toque;
4. encaminha pressionamento, soltura e arrasto para o elemento atingido.

A classe `ad` armazena retângulos de interação com `x`, `y`, `width` e `height`. O método de teste verifica se o ponto está dentro desses limites.

O jogo também implementa `keyPressed`, `keyReleased` e consulta `GameCanvas.getKeyStates()`. Portanto, há caminho de entrada por teclado além dos eventos de toque. Não é correto tratar este exemplar como jogo exclusivamente de teclado.

## 4. Recursos extraídos

A extração identificou 759 registros distribuídos por pacotes internos `RP*.rp`:

| Tipo identificado | Quantidade | Interpretação |
|---|---:|---|
| PNG | 248 | Imagens e sprites |
| MIDI | 19 | Música e/ou áudio MIDI |
| `0xFE` binário | 248 | Recursos proprietários ainda não totalmente decodificados |
| `0xF7` binário | 239 | Recursos proprietários ainda não totalmente decodificados |
| `0xFA` | 5 | Tabelas de texto/localização |

As imagens PNG foram extraídas e tiveram suas dimensões identificadas. Há spritesheets, ilustrações e telas de dimensões variadas. Os dois grupos binários proprietários não devem ser confundidos com arquivos corrompidos: eles são registros internos do formato de recursos do jogo e precisam de decodificação própria.

### Texto e idiomas

As cinco tabelas `0xFA` possuem 136 posições cada. Os idiomas encontrados são:

- inglês;
- francês;
- alemão;
- italiano;
- espanhol.

Não foi encontrada uma tabela portuguesa entre os recursos extraídos. O menu menciona outras opções de idioma, mas isso, isoladamente, não prova que os respectivos arquivos completos estejam presentes no JAR.

### Conteúdo de jogo que os textos confirmam

Os textos originais descrevem um jogo de gerenciamento/arcade:

- iniciar um turno pelo ícone de xícara;
- esperar os clientes sentarem;
- selecionar o botão de cor correspondente ao pedido;
- lançar a comida no prato;
- melhorar o humor dos clientes acertando o prato;
- ganhar mais gorjetas com clientes mais satisfeitos;
- completar pedidos até o cronômetro do turno terminar;
- investir dinheiro em melhorias;
- comprar mais tempo de operação, mesas, tipos de comida e fama;
- desbloquear modo livre e construir novos restaurantes;
- consultar relatórios de progresso e resultados do turno.

As métricas de fim de etapa incluem clientes satisfeitos, clientes atendidos, clientes perdidos e renda da etapa.

Os nomes de localidades/etapas presentes nas strings são: Street, Alps, GearCity, HongKong, BandShow, BattleField, UnderWorld e WhiteHouse.

## 5. Créditos internos do jogo

A tabela inglesa contém os créditos:

- © 2010;
- desenvolvido e produzido por **3 Dynamics (Asia) Limited**;
- distribuído por **THQ**;
- e-mail histórico de suporte impresso no jogo: `support@3dynamics.com`.

Isso é evidência de crédito contida no exemplar analisado, não uma confirmação de que aquele e-mail ainda funciona ou de quem detém atualmente os direitos. Para um pedido de autorização, será necessário verificar o titular atual e a sucessão de direitos.

## 6. APIs utilizadas pelo JAR

A análise do bytecode encontrou referências às seguintes APIs externas:

| API | Uso identificado |
|---|---|
| `MIDlet` | Ciclo de vida |
| `Display` / `Displayable` | Exibição e dimensões |
| `Canvas` | Tela cheia e eventos de toque |
| `GameCanvas` | Buffer gráfico e leitura de teclas |
| `Graphics` | Desenho 2D, clipping, tradução e pixels |
| `Image` | Carregamento e leitura de pixels |
| `Font` | Métricas e texto |
| `Command` | Criação de um comando |
| MMAPI `Manager` / `Player` | Reprodução de mídia |
| `VolumeControl` | Controle de volume |
| RMS `RecordStore` | Armazenamento persistente |

Não foram identificadas referências a namespaces proprietários `com.nokia.*` no conjunto de referências externas examinado. Isso reduz a necessidade aparente de APIs específicas de fabricante, mas não substitui um teste de execução.

## 7. Compatibilidade estática com o J2ME-Loader enviado

O código-fonte J2ME-Loader dentro de `NOKIA ASHA 500 — ENGENHARIA REVERSA.zip` contém implementações correspondentes às APIs usadas pelo Meteor Café:

| Necessidade do Meteor Café | Código-fonte J2ME-Loader | Avaliação estática |
|---|---|---|
| `GameCanvas` | Implementado | Compatível na API consultada |
| Callbacks de toque | Implementados em `Canvas` | Compatível na API consultada |
| Coordenadas escaladas de toque | Conversão para tela virtual | Favorável |
| `Command` | Construtor MIDP implementado | Compatível na API consultada |
| `Graphics` | Métodos consultados presentes | Compatível na API consultada |
| `Image` | `createImage` e `getRGB` presentes | Compatível na API consultada |
| MMAPI `createPlayer(InputStream,String)` | Implementado | Favorável |
| MIDI | tipos `audio/midi` e `audio/x-midi` aceitos pelo Manager | Favorável; validar áudio real |
| `Player` / `VolumeControl` | Interfaces/implementações presentes | Favorável |
| RMS `RecordStore` | Implementado | Compatível na API consultada |
| Recursos dentro do JAR | `AppClassLoader` lê recursos do pacote | Favorável |

O perfil padrão do J2ME-Loader no código consultado usa tela virtual de 240 × 320, mantém a proporção e deixa `touchInput` habilitado por padrão. A classe `Canvas` transforma os toques Android em coordenadas da tela virtual e dispara os eventos MIDP.

**Conclusão estática:** J2ME-Loader é o candidato mais forte do nosso acervo para tentar executar primeiro o JAR original sem reescrever o jogo. Há correspondência entre as APIs exigidas pelo Meteor Café e as implementações presentes no código-fonte enviado.

**Limite dessa conclusão:** não foi realizado ainda um teste real de inicialização, áudio, salvamento ou jogabilidade no Android. Compatibilidade de assinatura não garante ausência de diferenças comportamentais.

## 8. Observação sobre construir o J2ME-Loader a partir do ZIP

O ZIP do repositório contém a pasta `app/src/main/cpp/mmapi_tsf/TinySoundFont/`, mas ela está vazia. O arquivo `.gitmodules` declara que esse diretório é um submódulo apontando para:

`https://github.com/schellingb/TinySoundFont.git`

Portanto, o ZIP baixado sem submódulos não é uma cópia completa para compilar o mecanismo TSF de áudio. Para compilar a partir do código-fonte, será preciso inicializar/restaurar os submódulos ou obter o código ausente. Isso não impede testar uma versão pronta do J2ME-Loader no Android.

O projeto também contém um product flavor chamado `midlet`, com um MIDlet de exemplo em código-fonte. Esse flavor não deve ser confundido com uma função automática que converte qualquer arquivo JAR em APK com um clique.

## 9. Estratégia recomendada

### Fase A — preservar e testar o jogo original
1. Manter o JAR original intacto e guardar seu SHA-256.
2. Instalar uma versão pronta do J2ME-Loader no Galaxy A05.
3. Importar este JAR sem alterar os recursos ou classes.
4. Testar abertura, menu, tutorial, toque, áudio, salvamento e retomada.

### Fase B — corrigir incompatibilidades comprovadas
1. Registrar qualquer erro ou diferença visual.
2. Relacionar o problema à API ou rotina correspondente.
3. Corrigir a camada de compatibilidade, evitando alterar o jogo quando possível.
4. Repetir os testes para verificar regressões.

### Fase C — APK dedicado
Se o teste de emulação funcionar, estudar um APK dedicado que incorpore o runtime necessário e abra diretamente Meteor Café, sem exigir que o usuário escolha o jogo numa biblioteca de emulador. A alternativa de reimplementar partes do jogo deve ficar reservada para incompatibilidades que não possam ser resolvidas com fidelidade na camada de execução.

---

## Conclusão

Meteor Café parece um bom primeiro caso de preservação: o JAR está estruturalmente íntegro, utiliza um conjunto de APIs Java ME relativamente comum, tem suporte nativo a toque no próprio código e inclui os recursos gráficos, MIDI e textos necessários à análise.

A decisão técnica mais prudente é **testar o JAR original no J2ME-Loader antes de descompilar ou reescrever o jogo**. Esse caminho tem maior potencial de preservar a lógica, os tempos, os sprites, o áudio e as regras originais.
