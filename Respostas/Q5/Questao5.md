
# API REST para o Sistema de Biblioteca

## Endpoints e Métodos HTTP

### Livros

- `GET /livros` - Lista todos os livros (com paginação)
- `GET /livros/{id}` - Obtém detalhes de um livro específico
- `POST /livros` - Adiciona um novo livro
- `PUT /livros/{id}` - Atualiza um livro existente
- `DELETE /livros/{id}` - Remove um livro
- `GET /livros/genero/{genero}` - Busca livros por gênero

### Autores

- `GET /autores` - Lista todos os autores
- `GET /autores/{id}` - Obtém detalhes de um autor específico
- `POST /autores` - Adiciona um novo autor
- `PUT /autores/{id}` - Atualiza um autor existente
- `DELETE /autores/{id}` - Remove um autor

### Empréstimos

- `GET /emprestimos` - Lista todos os empréstimos
- `GET /emprestimos/usuario/{usuarioId}` - Lista empréstimos de um usuário
- `GET /emprestimos/ativos` - Lista empréstimos ativos
- `POST /emprestimos` - Cria um novo empréstimo
- `PUT /emprestimos/{id}/devolucao` - Registra devolução de um livro

## Formatos de Request/Response

### Exemplo: Obter detalhes de um livro com informações do autor

**Request:**

```
GET /livros/123?include=autor
```

**Response (200 OK):**

```json
{
  "id": 123,
  "titulo": "Dom Casmurro",
  "isbn": "978-85-359-0275-5",
  "genero": "Romance",
  "anoPublicacao": 1899,
  "quantidadeDisponivel": 3,
  "autor": {
    "id": 456,
    "nome": "Machado de Assis",
    "nacionalidade": "Brasileiro",
    "dataNascimento": "1839-06-21"
  }
}
```

### Exemplo: Listar todos os empréstimos ativos de um usuário com detalhes dos livros

**Request:**

```
GET /emprestimos/usuario/789?status=ativo&include=livro
```

**Response (200 OK):**

```json
[
  {
    "id": 101,
    "dataEmprestimo": "2023-05-10",
    "dataDevolucaoPrevista": "2023-06-10",
    "status": "ativo",
    "livro": {
      "id": 123,
      "titulo": "Dom Casmurro",
      "autor": "Machado de Assis"
    }
  },
  {
    "id": 102,
    "dataEmprestimo": "2023-05-15",
    "dataDevolucaoPrevista": "2023-06-15",
    "status": "ativo",
    "livro": {
      "id": 124,
      "titulo": "Memórias Póstumas de Brás Cubas",
      "autor": "Machado de Assis"
    }
  }
]
```

### Exemplo: Buscar livros por gênero, ordenados por popularidade

**Request:**

```
GET /livros/genero/Romance?sort=-popularidade
```

**Response (200 OK):**

```json
[
  {
    "id": 123,
    "titulo": "Dom Casmurro",
    "autor": "Machado de Assis",
    "popularidade": 95,
    "quantidadeDisponivel": 3
  },
  {
    "id": 125,
    "titulo": "A Moreninha",
    "autor": "Joaquim Manuel de Macedo",
    "popularidade": 82,
    "quantidadeDisponivel": 5
  }
]
```

# API GraphQL para o Sistema de Biblioteca

## Schema Definition

```graphql
type Livro {
  id: ID!
  titulo: String!
  isbn: String
  genero: String!
  anoPublicacao: Int
  quantidadeDisponivel: Int!
  autor: Autor!
  popularidade: Int
}

type Autor {
  id: ID!
  nome: String!
  nacionalidade: String
  dataNascimento: String
  livros: [Livro!]!
}

type Emprestimo {
  id: ID!
  usuario: Usuario!
  livro: Livro!
  dataEmprestimo: String!
  dataDevolucaoPrevista: String!
  dataDevolucaoReal: String
  status: String!
}

type Usuario {
  id: ID!
  nome: String!
  email: String!
  emprestimosAtivos: [Emprestimo!]!
}

type Query {
  livro(id: ID!): Livro
  livros(limit: Int, offset: Int): [Livro!]!
  livrosPorGenero(genero: String!, sortBy: String): [Livro!]!
  autor(id: ID!): Autor
  autores: [Autor!]!
  emprestimo(id: ID!): Emprestimo
  emprestimosAtivos: [Emprestimo!]!
  emprestimosPorUsuario(usuarioId: ID!): [Emprestimo!]!
}

type Mutation {
  adicionarLivro(input: LivroInput!): Livro!
  atualizarLivro(id: ID!, input: LivroInput!): Livro!
  removerLivro(id: ID!): Boolean!
  adicionarAutor(input: AutorInput!): Autor!
  atualizarAutor(id: ID!, input: AutorInput!): Autor!
  removerAutor(id: ID!): Boolean!
  criarEmprestimo(input: EmprestimoInput!): Emprestimo!
  registrarDevolucao(id: ID!): Emprestimo!
}

input LivroInput {
  titulo: String!
  isbn: String
  genero: String!
  anoPublicacao: Int
  quantidadeDisponivel: Int!
  autorId: ID!
}

input AutorInput {
  nome: String!
  nacionalidade: String
  dataNascimento: String
}

input EmprestimoInput {
  usuarioId: ID!
  livroId: ID!
  dataDevolucaoPrevista: String!
}
```

## Exemplos de Queries

### 1. Obter detalhes de um livro com informações do autor

```graphql
query {
  livro(id: "123") {
    titulo
    isbn
    genero
    anoPublicacao
    quantidadeDisponivel
    autor {
      nome
      nacionalidade
      dataNascimento
    }
  }
}
```

**Response:**

```json
{
  "data": {
    "livro": {
      "titulo": "Dom Casmurro",
      "isbn": "978-85-359-0275-5",
      "genero": "Romance",
      "anoPublicacao": 1899,
      "quantidadeDisponivel": 3,
      "autor": {
        "nome": "Machado de Assis",
        "nacionalidade": "Brasileiro",
        "dataNascimento": "1839-06-21"
      }
    }
  }
}
```

### 2. Listar todos os empréstimos ativos de um usuário com detalhes dos livros

```graphql
query {
  emprestimosPorUsuario(usuarioId: "789") {
    id
    dataEmprestimo
    dataDevolucaoPrevista
    status
    livro {
      id
      titulo
      autor {
        nome
      }
    }
  }
}
```

**Response:**

```json
{
  "data": {
    "emprestimosPorUsuario": [
      {
        "id": "101",
        "dataEmprestimo": "2023-05-10",
        "dataDevolucaoPrevista": "2023-06-10",
        "status": "ativo",
        "livro": {
          "id": "123",
          "titulo": "Dom Casmurro",
          "autor": {
            "nome": "Machado de Assis"
          }
        }
      },
      {
        "id": "102",
        "dataEmprestimo": "2023-05-15",
        "dataDevolucaoPrevista": "2023-06-15",
        "status": "ativo",
        "livro": {
          "id": "124",
          "titulo": "Memórias Póstumas de Brás Cubas",
          "autor": {
            "nome": "Machado de Assis"
          }
        }
      }
    ]
  }
}
```

### 3. Buscar livros por gênero, ordenados por popularidade

```graphql
query {
  livrosPorGenero(genero: "Romance", sortBy: "popularidade") {
    id
    titulo
    autor {
      nome
    }
    popularidade
    quantidadeDisponivel
  }
}
```

**Response:**

```json
{
  "data": {
    "livrosPorGenero": [
      {
        "id": "123",
        "titulo": "Dom Casmurro",
        "autor": {
          "nome": "Machado de Assis"
        },
        "popularidade": 95,
        "quantidadeDisponivel": 3
      },
      {
        "id": "125",
        "titulo": "A Moreninha",
        "autor": {
          "nome": "Joaquim Manuel de Macedo"
        },
        "popularidade": 82,
        "quantidadeDisponivel": 5
      }
    ]
  }
}
```

# Comparação entre REST e GraphQL

## Caso 1: Obter detalhes de um livro com informações do autor

**REST:**

- Requer endpoint específico com parâmetro para incluir autor
- Retorna estrutura fixa, pode conter dados desnecessários
- Fácil cache

**GraphQL:**

- Cliente especifica os campos desejados
- Única query pode buscar livro e autor
- Evita over-fetching
- Cache mais complexo

**Melhor abordagem:** GraphQL

## Caso 2: Listar todos os empréstimos ativos de um usuário com detalhes dos livros

**REST:**

- Requer múltiplos endpoints e parâmetros
- Pode envolver múltiplas requisições
- Estrutura fixa

**GraphQL:**

- Única query com dados relacionais
- Menos over-fetching
- Flexível

**Melhor abordagem:** Depende do caso

## Caso 3: Buscar livros por gênero, ordenados por popularidade

**REST:**

- Endpoint e parâmetros dedicados
- Paginação simples

**GraphQL:**

- Filtros e ordenações flexíveis
- Seleção de campos personalizada

**Melhor abordagem:** Ambos funcionam bem

# Considerações Finais

## Quando usar REST:

- API simples e previsível
- Cache crítico
- Todos os dados sempre necessários
- Equipe familiarizada com REST

## Quando usar GraphQL:

- Necessidades de dados variáveis
- Redução de over-fetching
- Consulta de dados relacionais em uma requisição
- Flexibilidade para evolução
