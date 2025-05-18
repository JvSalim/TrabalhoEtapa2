# Questao 7

## Caso 1: Uma aplicação legada escrita em uma tecnologia antiga que precisa de bibliotecas específicas
### Modelo Ideal: IaaS (Infrastructure as a Service)

→ **Justificativa:**

Aplicações legadas costumam ser escritas em linguagens desatualizadas (como Delphi, COBOL ou versões antigas de Java) e frequentemente dependem de bibliotecas específicas, versões exatas de sistemas operacionais ou de ambientes de execução customizados.
Nesse contexto, os modelos mais abstraídos como PaaS ou FaaS não são recomendados, pois:

* Eles impõem restrições sobre o ambiente de execução.
* Não permitem controle fino sobre sistema operacional, drivers ou configurações antigas.

O modelo IaaS oferece justamente o controle necessário:

* Você provisiona uma máquina virtual com o sistema operacional de sua escolha.
* Pode instalar exatamente as dependências que a aplicação exige.
* Garante compatibilidade com bibliotecas antigas ou customizações específicas.

→ **Exemplo:** Usar uma VM no AWS EC2 ou Azure Virtual Machines com Windows Server 2008 + Java 6.

→ **Resumo:** IaaS é ideal quando se precisa de controle total sobre o ambiente.

## Caso 2: Um site de e-commerce que precisa escalar durante períodos de alta demanda
### Modelo Ideal: PaaS (Platform as a Service)

→ **Justificativa:**

Plataformas de e-commerce exigem:

* Alta disponibilidade.
* Capacidade de escalar automaticamente (por exemplo, em datas como Black Friday).
* Gerenciamento de infraestrutura simplificado.

O modelo PaaS abstrai a complexidade de configuração e manutenção da infraestrutura, permitindo que os desenvolvedores foquem no código da aplicação.

* Plataformas como Heroku, Google App Engine ou Azure App Services oferecem autoescalabilidade, balanceamento de carga, deploy contínuo e integração com bancos de dados e cache.

PaaS é preferível ao IaaS neste caso porque:

* Elimina a necessidade de gerenciar VMs, sistemas operacionais ou balanceadores de carga manualmente.
* Escalabilidade é automática com base em demanda.

FaaS não seria adequado aqui porque o e-commerce exige sessões persistentes, latência controlada e controle de estado, o que é limitado em arquiteturas serverless.

→ **Resumo:** PaaS oferece uma plataforma gerenciada e escalável ideal para aplicações web com variação de carga.

## Caso 3: Um sistema de processamento de imagens que recebe arquivos esporadicamente
### Modelo Ideal: FaaS (Function as a Service / Serverless)

→ **Justificativa**:

O processamento esporádico de arquivos é um cenário clássico para computação serverless (FaaS), pois:

* A aplicação não precisa ficar "rodando" continuamente.
* O código é executado apenas quando um evento ocorre (ex: upload de uma imagem).
* Paga-se apenas pelo tempo de execução, o que reduz custos drasticamente em comparação a manter uma VM ou container sempre ativo.

Plataformas como AWS Lambda, Azure Functions e Google Cloud Functions permitem:

* Executar funções sob demanda.
* Escalar automaticamente com base em eventos (ex: novos arquivos no storage).
* Eliminar a necessidade de manter servidores ou containers provisionados.

→ **Resumo:** FaaS é ideal quando a carga de trabalho é baseada em eventos e não contínua, com ótimo custo-benefício e simplicidade operacional.

## Caso 4: Um sistema de gestão de RH para uma empresa média
### Modelo Ideal: SaaS (Software as a Service)

→ **Justificativa:**

Soluções de RH (folha de pagamento, férias, ponto, avaliação, recrutamento) já estão amplamente disponíveis no modelo SaaS:

* Ex: Gupy, TOTVS, SAP SuccessFactors, ADP, Convenia.

A empresa média não precisa (nem quer) desenvolver ou manter seu próprio sistema de RH:

* Busca soluções prontas, atualizadas automaticamente e que sigam conformidades legais e trabalhistas.
* Reduz custo com equipe técnica, manutenção, infraestrutura e segurança.

SaaS permite:

* Acesso via navegador ou aplicativo.
* Plano de assinatura mensal/anual.
* Suporte e atualizações incluídas.

→ **Resumo:** SaaS entrega uma solução completa e pronta para uso, ideal para empresas que não querem se preocupar com desenvolvimento.

## Caso 5: Um aplicativo móvel com backend para sincronização de dados
### Modelo Ideal: BaaS ou PaaS (com leve preferência por PaaS)

→ **Justificativa:**

Aplicativos móveis frequentemente precisam de:

* Backend para autenticação, sincronização, notificações push, armazenamento em nuvem.
* APIs REST/GraphQL com suporte à escalabilidade.

Há duas abordagens viáveis:

**a)** PaaS (Platform as a Service):
Ideal se o time quer controle sobre a lógica de negócio, estrutura de banco de dados, etc.

* Ex: Firebase Hosting com Google App Engine, Azure App Services.
* Você constrói seu backend com Node.js, Django, Spring etc., hospedado numa plataforma escalável.

**b)** BaaS (Backend as a Service):
Alternativa mais rápida e plug-and-play.

* Ex: Firebase, AWS Amplify, Supabase.
* Backend já pronto com autenticação, banco de dados, storage e push notifications.

A escolha entre PaaS e BaaS depende do nível de customização necessário. Se for um app simples ou protótipo, BaaS é suficiente. Se requer lógica mais complexa ou integração com sistemas externos, PaaS é melhor.

→ **Resumo:**
Para sincronização de dados, notificações e autenticação, BaaS ou PaaS são ideais.
PaaS é mais flexível; BaaS é mais rápido para começar.


