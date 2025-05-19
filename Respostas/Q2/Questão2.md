# Questão 2

## 1. Facilidade de Desenvolvimento

### Java RMI
- **Vantagens**:
  - Totalmente integrado com o ambiente Java
  - Baixa curva de aprendizagem para programadores em Java
  - Não precisa de utilizar bibliotecas externas

- **Desvantagens**:
  - Configuração manual
  - Funciona apenas em Java
  - Dificuldade com redes externas

### gRPC
- **Vantagens**:
  - Suporte a várias linguagens de programação
  - Baseado em HTTP/2 com suporte a TLS e streaming
  - Ideal para ambientes distribuídos e microserviços

- **Desvantagens**:
  - Necessita da instalação do `protoc` e a definição de `.proto`
  - Integração com ferramentas de build
  - Maior curva de aprendizagem para iniciantes

## 2. Complexidade Sintática do Código

| Aspecto                 | Java RMI                                  | gRPC                            |
|-------------------------|-------------------------------------------|---------------------------------|
| Interface remota        | Extende `Remote`, lança `RemoteException` | Definida em `.proto`            |
| Geração de código       | Manual (antigamente com `rmic`)           | Automática via `protoc`         |
| Cliente e servidor      | Mais código para registry e bind          | Gerado automaticamente          |
| Complexidade na escrita | Alta                                      | Menor após configuração inicial |

## 3. Limitações e Considerações

| Critério                    | Java RMI                 | gRPC                                         |
|-----------------------------|--------------------------|----------------------------------------------|
| **Multiplataforma**         | Não                      | Sim                                          |
| **Firewall/NAT**            | Difícil                  | HTTP/2 facilita                              |
| **Escalabilidade**          | Limitada                 | Alta                                         |
| **Desempenho**              | Bom em redes locais      | Excelente                                    |
| **Streaming**               | Não suportado            | Sim                                          |
| **Facilidade de Debug**     | Alta                     | Requer configuração de logs e interceptors   |