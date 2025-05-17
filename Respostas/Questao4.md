A solução deve:

- Receber pedidos de múltiplas origens (Web, Mobile, API)
- Distribuir o processamento entre múltiplos workers
- Garantir o processamento exatamente uma vez
- Implementar um mecanismo de retry para mensagens que falham no processamento
- Lidar com diferentes cenários de falha

## Arquitetura Proposta

### Componentes

- **Produtores:** Web, Mobile, API
- **Broker de Mensagens:** RabbitMQ, Kafka, Amazon SQS (com suporte a FIFO e ACK)
- **Consumers (Workers):** Microsserviços para processar os pedidos
- **Banco de Dados:** Armazena o estado dos pedidos
- **Dead Letter Queue (DLQ):** Armazena mensagens que falharam após múltiplas tentativas

## Funcionamento Geral

1. **Recebimento dos Pedidos**
   - Web, mobile ou APIs enviam pedidos.
   - A API central valida o pedido e publica a mensagem na fila principal (`pedidos_queue`).

2. **Distribuição para Workers**
   - A fila entrega mensagens para múltiplos consumidores concorrentes (workers).
   - Cada worker processa uma mensagem por vez.

3. **Garantia de Exactly Once**
   - Utilizar deduplicação com `pedido_id` no banco de dados:
     - Antes de processar, o worker consulta se o pedido já foi processado.
     - Se sim, ignora.
     - Se não, processa e registra como processado.
   - Isso evita duplicidade mesmo que a mensagem seja reentregue.

4. **Retry Automático**
   - Se um worker falhar antes de confirmar (ACK), a fila reentrega a mensagem.
   - Após N tentativas, a mensagem é enviada para uma **Dead Letter Queue (DLQ)**.

## Cenários de Falha e Solução

| Cenário | Solução |
|--------|---------|
| **Worker falha durante o processamento** | Broker não recebe ACK → mensagem é reentregue. Worker ignora se já foi processada. |
| **API envia o mesmo pedido mais de uma vez** | Deduplicação por `pedido_id` evita reprocessamento. |
| **Worker processa, mas falha ao salvar no banco** | Mensagem é reentregue. Worker tenta novamente. Uso de etapas idempotentes. |
| **Pedido inválido ou com erro permanente** | Após N falhas, mensagem é enviada para a DLQ para revisão manual. |


## Tecnologias para a solução

- **Fila de Mensagens:** 
  - Kafka (com controle de offset e partições)
  - RabbitMQ (com ACK/NACK)
  - Amazon SQS FIFO (com deduplicação)
  
- **Banco de Dados:** 
  - PostgreSQL com `pedido_id` como chave única

- **Workers:** 
  - Escritos em Python, Java, Node.js, etc.

- **Retry & DLQ:** 
  - Configuração nativa no broker ou manual com outra fila auxiliar

## Resumo das Garantias

| Requisito                         | Garantia Implementada                        |
|----------------------------------|----------------------------------------------|
| Várias origens                   | API publica pedidos na fila                  |
| Múltiplos workers                | Fila com múltiplos consumidores              |
| Exactly-once                     | Deduplicação + controle de estado com `pedido_id` |
| Retry automático                 | ACK/NACK com reentrega + Dead Letter Queue   |
| Tolerância a falhas              | Mensagens persistidas e reentregues após falhas |

