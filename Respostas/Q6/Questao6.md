# Questao 6

## Arquitetura da Aplicação

* Armazenamento -> Amazon S3
  * Um bucket para os usuários fazerem o upload das imagens
* Gatilho -> AWS Lambda com EventBridge ou S3 Trigger
  * Um trigger é configurado no S3 para chamar uma função Lambda sempre que um novo arquivo for carregado 
* Processamento -> AWS Lambda
  * Verificar o tamanho da imagem
  * Gerar múltiplos tamanhos 
  * Extrair metadados 
  * Armazenar imagens redimensionadas 
* Banco de Dados -> Amazon DynamoDB
  * Os metadados extraídos são armazenados na tabela Metadados
  * Chave primária: imagemId (UUID)
  * Outros campo da tabela: url, tamanho, etc
* Escalabilidade Automática
  * Cada upload de imagem chama uma nova instância da função Lambda, que escala automaticamente
  * S3 e DynamoDB são altamente escaláveis, pois suportam um grande volume de leitura e escrita sem a necessidade de configuração manual

## Benefícios 

* Baixo custo
* Alta escalabilidade
* Manutenção reduzida

