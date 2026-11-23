# Instalação no Android

## Baixar o APK

1. Abra [Compilar METEOR CAFE 1.1.0](https://github.com/ChatSamuel/meteor-cafe-apk/actions/workflows/build-meteor.yml).
2. Abra a execução mais recente que terminou com **Success**.
3. Em **Artifacts**, baixe `METEOR-CAFE-1.1.0-ANDROID`.
4. Extraia o ZIP do artifact. O instalador é `METEOR_CAFE_1.1.0.apk`.

## Instalar

1. Abra o arquivo APK pelo app Arquivos/Meus Arquivos do Android.
2. Se solicitado, permita a instalação de apps dessa origem (navegador ou gerenciador de arquivos).
3. Volte ao instalador e confirme.
4. Procure **Meteor Cafe** na lista de aplicativos. O nome exibido não contém “Debug” nem “Teste”.

## Se o Android recusar uma atualização

O Android só aceita atualização por cima de um app existente quando o identificador do pacote e o certificado de assinatura são compatíveis. Um APK de desenvolvimento produzido em outro ambiente pode ter uma chave diferente. Se aparecer conflito de pacote/assinatura, não desinstale imediatamente se quiser preservar os dados: primeiro confirme se há progresso salvo que precise ser mantido. Remover o app pode apagar dados locais.

## Disponibilidade

Os artifacts do GitHub Actions são temporários (atualmente 14 dias no workflow). Ainda não há Release permanente do APK neste repositório. Uma Release pública deverá aguardar a verificação dos direitos e a autorização aplicável.
