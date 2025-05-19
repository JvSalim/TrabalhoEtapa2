# Questão 1

# **Abordagem de Microsserviços para Streaming de Vídeo**

---

## **1. Arquitetura Geral**

Um sistema de streaming baseado em microsserviços divide as funcionalidades em serviços independentes, cada um com sua própria responsabilidade. Vamos usar tecnologias mais acessíveis:

```

```

### **Fluxo de Funcionamento:**

![Diagrama de Fluxo de Funcionamento](imagens/DiagramaQ1.png 'Fluxo de Funcionamento')

1. O usuário acessa o **frontend** (site ou app).
2. O frontend se comunica com o **API Gateway** (Nginx), que roteia as requisições para os microsserviços corretos.
3. Cada microsserviço executa sua função (ex.: buscar catálogo, autenticar usuário).
4. Dados são armazenados em **bancos de dados** (MySQL para dados estruturados, Redis para cache).
5. Os vídeos são armazenados no **Amazon S3** e entregues via **CDN** (Cloudflare) para melhor desempenho.

![Diagrama de Fluxo das Requisições](imagens/Requisicoes.png 'Fluxo das Requisições')

---

## **2. Microsserviços Principais (Explicação Detalhada)**

### **1. Serviço de Catálogo**

**Responsabilidade**: Gerenciar informações sobre filmes, séries e documentários.  
**Tecnologias**:

- **Backend**: Node.js (Express) ou Python (Flask)  
- **Banco de Dados**: MySQL (para dados estruturados)  
- **Cache**: Redis (para acelerar buscas frequentes)  

**Funcionalidades**:

- Listar filmes/séries por categoria (ação, comédia, etc.).
- Buscar informações detalhadas (elenco, diretor, duração).
- Gerenciar temporadas e episódios.

**Exemplo de Chamada API**:

```http
GET /api/catalog?genre=action
Resposta:
{
  "movies": [
    {
      "id": 1,
      "title": "The Dark Knight",
      "genre": "action",
      "duration": "152 min"
    }
  ]
}
```

---

### **2. Serviço de Usuários**

**Responsabilidade**: Gerenciar cadastro, login e perfis.  
**Tecnologias**:

- **Backend**: PHP (Laravel) ou Java (Spring Boot)  
- **Banco de Dados**: PostgreSQL (mais robusto para dados de usuários)  
- **Autenticação**: JWT (tokens seguros)  

**Funcionalidades**:

- Cadastro/login (email/senha ou Google/Facebook).
- Gerenciamento de perfis (criança, adulto).
- Histórico de assistidos.

**Exemplo de Chamada API**:

```http
POST /api/login
Body: { "email": "user@example.com", "password": "123456" }
Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": { "id": 1, "name": "João Silva" }
}
```

---

### **3. Serviço de Player**

**Responsabilidade**: Reproduzir vídeos de forma eficiente.  
**Tecnologias**:

- **Frontend**: HTML5 + Video.js (biblioteca JavaScript para player)  
- **Protocolo de Streaming**: HLS (HTTP Live Streaming)  
- **CDN**: Cloudflare (para entrega rápida)  

**Funcionalidades**:

- Suporte a múltiplas qualidades (480p, 1080p, 4K).
- Legendas (opcionais).
- Controles de play/pause/volume.

**Exemplo de Implementação**:

```html
<video controls>
  <source src="https://cdn.streaming.com/movie_1080p.m3u8" type="application/x-mpegURL">
  <track src="subtitles_pt.vtt" kind="subtitles" srclang="pt" label="Português">
</video>
```

---

### **4. Serviço de Recomendações**

**Responsabilidade**: Sugerir vídeos baseados no histórico.  
**Tecnologias**:

- **Backend**: Python (Flask)  
- **Banco de Dados**: MongoDB (para armazenar preferências)  
- **Algoritmo**: Filtro colaborativo simples ("quem viu X também viu Y")  

**Funcionalidades**:

- Recomendar filmes similares.
- Listas personalizadas ("Baseado no que você assistiu").

**Exemplo de Chamada API**:

```http
GET /api/recommendations?user_id=1
Resposta:
{
  "recommendations": [
    { "id": 2, "title": "Inception", "match": "85%" },
    { "id": 3, "title": "Interstellar", "match": "78%" }
  ]
}
```

---

### **5. Serviço de Autenticação e Autorização**

**Responsabilidade:** Garantir acesso seguro e gerenciamento de permissões.
**Tecnologias:**

- Autenticação: OAuth 2.0, JWT
- Autorização: OpenID Connect, Keycloak
- Proteção de Conteúdo: DRM (Widevine, PlayReady, FairPlay)

**Funcionalidades:**

- Login com provedores sociais (Google, Facebook)
- Controle de sessão por dispositiv
- Permissões com base no plano (ex: “Premium” assiste em 4K)

- Exemplo de Chamada API:

```http
GET /api/user/permissions
Headers: Authorization: Bearer {jwt_token}
Resposta:

{
"user_id": 42,
"plan": "premium",
"permissions": ["watch_4k", "offline_download", "multi_device"]
}
```

---

### **6. Serviço de Monitoramento e Analytics**

**Responsabilidade:** Coletar dados operacionais e de comportamento do usuário.
**Tecnologias:**

- Coleta: Beats, Logstash
- Análise: Elasticsearch
- Visualização: Grafana, Kibana
- Alertas: Prometheus + Alertmanager

**Funcionalidades:**

- Monitorar qualidade do streaming (buffering, bitrate)
- Mapear comportamento do usuário (tempo assistido, abandono)
- Geração de relatórios para decisão de negócio

- Exemplo de Métricas Coletadas:

```http
{
"video_id": 103,
"user_id": 87,
"bitrate_avg": "3200 kbps",
"buffer_events": 2,
"watch_time": "36m"
}
```

---

### **7. Serviço de Pagamentos**

**Responsabilidade:** Processar cobranças, gerenciar planos e promoções.
**Tecnologias:**

- Gateways: Stripe, PayPal
- Banco de Dados: PostgreSQL
- Segurança: PCI-DSS compliance, criptografia ponta-a-ponta

**Funcionalidades:**

- Assinatura mensal/anual com renovação automática
- Aplicação de cupons promocionais
- Emissão de nota fiscal

- Exemplo de Chamada API:

```http
POST /api/payment/subscribe
Body:

{
"user_id": 1,
"plan": "premium",
"payment_method": "card",
"coupon": "BEMVINDO20"
}
```

```http
Resposta:

{
"status": "success",
"next_billing": "2025-06-15",
"discount": "20%"
}
```

---

### Vantagens da Abordagem de Microsserviços

1. **Escalabilidade Granular**:
   - Cada serviço pode ser escalado independentemente conforme demanda
   - Ex: Serviço de transcodificação pode usar mais instâncias durante lançamentos

2. **Resiliência Aprimorada**:
   - Falha em um serviço não derruba todo o sistema
   - Circuit breakers (Hystrix) previnem cascata de falhas

3. **Ciclos de Desenvolvimento Independentes**:
   - Times podem trabalhar em paralelo com diferentes tecnologias
   - Deploy contínuo sem afetar todo o sistema

4. **Otimização Tecnológica**:
   - Linguagens/frameworks específicos para cada domínio
   - Ex: Go para entrega de vídeo, Python para ML

5. **Facilidade de Evolução**:
   - Substituição gradual de componentes
   - Experimentação com novos recursos isoladamente

## Abordagem Cliente-Servidor Tradicional para Plataforma de Streaming

### Arquitetura Monolítica Detalhada

#### Componentes Principais

1. **Aplicação Centralizada**:
   - Único artefato de deploy contendo todas funcionalidades
   - Camadas:
     - Apresentação (Thymeleaf, JSF)
     - Lógica de Negócio (Spring, .NET Core)
     - Acesso a Dados (Hibernate, Entity Framework)

2. **Banco de Dados Relacional**:
   - Tabelas para: usuários, conteúdos, transações, logs
   - Schema complexo com múltiplos joins
   - Normalização para evitar redundância

3. **Servidor de Aplicação**:
   - Tomcat, JBoss, IIS
   - Configuração de cluster para alta disponibilidade
   - Session replication entre nós

4. **Sistema de Arquivos**:
   - Armazenamento centralizado de vídeos (NAS, SAN)
   - Estrutura de diretórios organizada por conteúdo

5. **Processamento Batch**:
   - Jobs agendados para:
     - Transcodificação
     - Relatórios
     - Limpeza de dados

#### Fluxo de Requisições

```

```

### Vantagens da Abordagem Tradicional

1. **Simplicidade Inicial**:
   - Único código base para desenvolver e testar
   - Debug mais direto com stack traces completos

2. **Transações ACID**:
   - Consistência forte nas operações
   - Operações complexas com múltiplas tabelas

3. **Performance em Chamadas Locais**:
   - Comunicação intra-processo mais rápida
   - Sem overhead de serialização/deserialização

4. **Monitoramento Unificado**:
   - Logs centralizados
   - Métricas consolidadas

5. **Segurança**:
   - Perímetro de segurança único
   - Mais fácil de auditar

## Comparação Detalhada das Abordagens

### 1. Escalabilidade

| **Critério**               | **Microsserviços**                                                                 | **Cliente-Servidor Tradicional**                                                  |
|----------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Escala Horizontal**       | Excelente - Cada serviço escala independentemente conforme demanda específica       | Limitada - Precisa escalar toda a aplicação mesmo para aumento em um componente   |
| **Padrão de Carga**        | Adaptável a padrões irregulares de tráfego (ex: pós-lançamentos)                   | Escala uniforme, pode subdimensionar ou superdimensionar recursos                |
| **Recursos**               | Otimização de recursos (CPU-heavy para transcodificação, memória para cache)       | Alocação uniforme de recursos para todos os componentes                          |
| **Exemplo Prático**        | 10x instâncias do serviço de entrega durante horário de pico, outros mantidos      | Duplicação de todo o stack mesmo que apenas o streaming precise de mais recursos |

### 2. Manutenção

| **Critério**               | **Microsserviços**                                                                 | **Cliente-Servidor Tradicional**                                                  |
|----------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Atualizações**           | Deploys independentes - pode atualizar um serviço sem afetar outros                | Deploy monolítico - qualquer mudança requer nova versão de toda a aplicação       |
| **Complexidade**           | Alta - Necessidade de orquestração, service discovery, monitoramento distribuído   | Baixa - Toda a lógica em um único lugar, mais fácil de entender inicialmente     |
| **Dependências**           | Versionamento cuidadoso de APIs entre serviços                                     | Controle centralizado de dependências                                             |
| **Exemplo Prático**        | Atualizar algoritmo de recomendações sem tocar no sistema de pagamentos            | Mesmo pequena correção no player requer deploy completo                          |

### 3. Resiliência

| **Critério**               | **Microsserviços**                                                                 | **Cliente-Servidor Tradicional**                                                  |
|----------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Isolamento de Falhas**   | Alta - Falha em um serviço não derruba todo o sistema (ex: recomendação offline)   | Baixa - Falha em qualquer componente pode derrubar toda a aplicação               |
| **Recuperação**            | Mais rápida - Pode reiniciar/replicar apenas o serviço problemático                | Mais lenta - Requer restart completo da aplicação                                 |
| **Circuit Breaker**        | Implementação natural - Serviços podem degradar graciosamente                      | Difícil implementação - Tudo ou nada                                              |
| **Exemplo Prático**        | Serviço de pagamentos pode falhar sem afetar reprodução de vídeos                  | Erro no módulo de autenticação impede acesso a todo o sistema                     |

### 4. Desempenho

| **Critério**               | **Microsserviços**                                                                 | **Cliente-Servidor Tradicional**                                                  |
|----------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Latência**               | Potencialmente maior devido a chamadas remotas entre serviços                      | Menor - Chamadas locais dentro do mesmo processo                                  |
| **Throughput**             | Maior capacidade agregada devido à escalabilidade granular                         | Limitado pelo gargalo do servidor monolítico                                      |
| **Overhead**               | Serialização/deserialização, taxas de rede                                         | Quase zero para comunicação interna                                               |
| **Exemplo Prático**        | Chamada HTTP entre serviços vs chamada de método Java direto                       | Operação complexa com múltiplos joins no banco vs chamadas entre serviços         |

### 5. Desenvolvimento

| **Critério**               | **Microsserviços**                                                                 | **Cliente-Servidor Tradicional**                                                  |
|----------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Curva de Aprendizado**   | Mais íngreme - Requer conhecimento em arquitetura distribuída                      | Mais suave - Paradigma bem estabelecido                                           |
| **Velocidade Inicial**     | Mais lenta - Necessidade de configurar infraestrutura básica                       | Mais rápida - Pode começar a codificar imediatamente                              |
| **Flexibilidade**          | Times podem escolher tecnologias adequadas para cada serviço                       | Tecnologia uniforme em todo o sistema                                             |
| **Testabilidade**          | Testes de integração mais complexos, mas testes unitários mais isolados            | Testes end-to-end mais simples, mas unitários podem ser mais difíceis             |

## Conclusão Técnica

A abordagem de **microsserviços** é superior para plataformas de streaming de vídeo modernas que:

- Esperam crescimento rápido e irregular
- Necessitam de alta disponibilidade
- Possuem componentes com requisitos técnicos distintos
- Operam em ambientes cloud nativos

A abordagem **cliente-servidor tradicional** pode ser adequada para:

- Projetos com escopo limitado e previsível
- Equipes pequenas com expertise consolidada
- Ambientes com restrições regulatórias específicas
- Sistemas legados que não justificam refatoração

Para uma plataforma como Netflix, Amazon Prime ou Disney+, a arquitetura de microsserviços é essencial para atender aos requisitos de escala global, disponibilidade contínua e rápida inovação. Já para um sistema interno corporativo com poucos usuários concorrentes, a abordagem tradicional pode oferecer melhor custo-benefício inicial.

A escolha deve considerar:

1. Tamanho e crescimento esperado da base de usuários
2. Capacidade da equipe de operar sistemas distribuídos
3. Orçamento para infraestrutura cloud
4. Necessidade de inovação e velocidade de lançamento de features
5. Requisitos de compliance e segurança
