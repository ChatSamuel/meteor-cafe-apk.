# Aplicar a organização no repositório existente

Este procedimento atualiza o repositório `ChatSamuel/meteor-cafe-apk` já existente. Não cria outro repositório e não altera a visibilidade.

## Envie dois arquivos

1. `METEOR_CAFE_REPO_UPDATE.zip` para a raiz do repositório.
2. `organize-repository.yml` para `.github/workflows/organize-repository.yml`.

O segundo arquivo também está dentro do pacote; envie-o separadamente para que possa ser executado uma única vez.

## Execute a organização

1. Abra **Actions**.
2. Selecione **Organizar repositório METEOR CAFE**.
3. Toque em **Run workflow** na branch `main`.
4. Aguarde a execução terminar com **Success**.

O workflow aplica README, documentação, screenshots, logo e fonte Android navegável em `android/`. Também troca o workflow de build pelo que compila a base estável 1.1.0.

Se o GitHub impedir o envio por falta de permissão, abra **Settings → Actions → General → Workflow permissions** e selecione **Read and write permissions**. Depois execute novamente.

## Após a organização

- Confira a página inicial e o README.
- Em **Actions**, execute **Compilar METEOR CAFE 1.1.0**.
- Baixe o artifact `METEOR-CAFE-1.1.0-ANDROID`.

O workflow de organização apaga a si próprio depois de aplicar o pacote; o workflow de compilação permanece. Os ZIPs antigos são removidos do branch atual, mas podem continuar acessíveis no histórico Git anterior. Nenhuma reescrita de histórico nem force-push é executada.
