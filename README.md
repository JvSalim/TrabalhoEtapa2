# Trabalho Etapa 2 Desenvolvimento de Aplicações Móveis e Distribuídas

## Membros: João Victor Salim, Maria Eduarda Gonçalves e Lucas Garcia

### Exercício 1: Modelo de Sistemas
Questão prática: Descreva um sistema de streaming de vídeo utilizando:

Uma abordagem de microsserviços
Uma abordagem cliente-servidor tradicional
Explique as diferenças entre as duas abordagens em termos de escalabilidade, manutenção e resiliência.


### Exercício 2: Invocação Remota (RMI e RPC)
Questão prática: Implemente um simples sistema calculadora usando:

Java RMI: Crie uma interface remota com operações básicas (soma, subtração, multiplicação, divisão)
gRPC: Defina o mesmo serviço usando Protocol Buffers e implemente o servidor e cliente
Compare as duas implementações quanto à facilidade de desenvolvimento, verbosidade do código e possíveis limitações.


### Exercício 3: Comunicação Indireta e Pub/Sub
Questão prática: Projete um sistema de notificações para uma plataforma de e-commerce usando o paradigma Pub/Sub. O sistema deve:

Permitir que usuários se inscrevam para receber atualizações sobre produtos específicos
Notificar sobre mudanças de preço, disponibilidade de estoque e promoções
Suportar diferentes canais de entrega (email, SMS, notificações push)
Desenhe o diagrama do sistema, identificando publicadores, assinantes, tópicos e filtros.


### Exercício 4: Fila de Mensagens
Questão prática: Um sistema de processamento de pedidos precisa garantir que cada pedido seja processado exatamente uma vez, mesmo em caso de falhas. Projete uma solução usando filas de mensagens que:

Receba pedidos de múltiplas origens (web, mobile, API)
Distribua o processamento entre múltiplos workers
Garanta processamento exatamente uma vez
Implemente um mecanismo de retry para mensagens que falham no processamento
Explique como sua solução lida com diferentes cenários de falha.


### Exercício 5: Web Services (SOAP vs REST vs GraphQL)
Questão prática: Para um sistema de biblioteca que gerencia livros, autores e empréstimos:

Projete uma API REST, definindo endpoints, métodos HTTP, formatos de request/response
Projete a mesma API usando GraphQL, definindo o schema (tipos, queries, mutations)
Compare as duas abordagens para os seguintes casos de uso:
Obter detalhes de um livro com informações do autor
Listar todos os empréstimos ativos de um usuário com detalhes dos livros
Buscar livros por gênero, ordenados por popularidade


### Exercício 6: Serverless e FaaS
Questão prática: Projete uma aplicação Serverless para processamento automático de imagens:

Quando uma imagem é enviada para um bucket de armazenamento, deve ser redimensionada em múltiplos tamanhos
Metadados da imagem devem ser extraídos e armazenados em um banco de dados
Para imagens grandes, deve ser gerada uma versão em miniatura (thumbnail)
Explique como ficaria uma solução usando serviços AWS (Lambda, S3, DynamoDB, etc.) ou equivalentes de outro provedor. Discuta como a solução escala automaticamente com o aumento de carga.

### Exercício 7: Modelos de Serviços em Cloud
Questão prática: Para cada um dos seguintes casos de uso, identifique o modelo de serviço em nuvem mais adequado (IaaS, PaaS, SaaS ou FaaS) e justifique sua escolha:

Uma aplicação legada escrita em uma tecnologia antiga que precisa de bibliotecas específicas
Um site de e-commerce que precisa escalar durante períodos de alta demanda
Um sistema de processamento de imagens que recebe arquivos esporadicamente
Um sistema de gestão de RH para uma empresa média
Um aplicativo móvel com backend para sincronização de dados
