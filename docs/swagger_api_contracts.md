# Contratos da API (Swagger/OpenAPI Docs)

Este documento centraliza o design e os contratos da API do Navalhex. A especificação OpenAPI 3.0 formal em formato YAML está disponível no arquivo [`swagger.yaml`](file:///c:/Users/luizc/Documents/navalhex/docs/swagger.yaml).

---

## Estrutura Padrão de Resposta (`ApiResponse<T>`)

Todas as respostas da API seguem o envelope unificado:

```json
{
  "status": 200,
  "message": "Mensagem descritiva",
  "data": { ... } | null,
  "errors": { "campo": "mensagem de erro" } | null
}
```

---

## 1. Módulo: Auth & Usuários (`/api/auth`)

### 1.1 Cadastro de Usuário
**POST** `/api/auth/register`

Registra um novo usuário com perfil (`ADMIN`, `TENANT`, `BARBER`, `CUSTOMER`).

#### Request Body (`application/json`)
```json
{
  "name": "João da Silva",
  "email": "joao@email.com",
  "password": "Password123@",
  "role": "TENANT",
  "whatsapp": "(11) 91234-5678"
}
```

**Regras de Validação:**
- `name`: Obrigatório, 3 a 100 caracteres.
- `email`: Obrigatório, formato de e-mail válido e único no sistema.
- `password`: Obrigatório, 8 a 60 caracteres. Requer no mínimo: 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial.
- `role`: Obrigatório (`ADMIN`, `TENANT`, `BARBER`, `CUSTOMER`).
- `whatsapp`: Obrigatório, formato brasileiro com DDD `(XX) 9XXXX-XXXX` ou `XX9XXXXXXXX`, único no sistema.

#### Respostas
- **201 Created**:
  ```json
  {
    "status": 201,
    "message": "Usuário cadastrado com sucesso!",
    "data": null,
    "errors": null
  }
  ```
- **400 Bad Request**: Validação falhou ou duplicidade de e-mail/whatsapp.

---

### 1.2 Login / Autenticação
**POST** `/api/auth/login`

Autentica o usuário e retorna o token JWT.

#### Request Body (`application/json`)
```json
{
  "email": "joao@email.com",
  "password": "Password123@"
}
```

#### Respostas
- **200 OK**:
  ```json
  {
    "status": 200,
    "message": "Login realizado com sucesso!",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "name": "João da Silva",
      "email": "joao@email.com",
      "role": "TENANT"
    },
    "errors": null
  }
  ```
- **400 Bad Request**: Credenciais inválidas.

---

## 2. Módulo: Tenants / Barbearias (`/api/tenants`)

### 2.1 Cadastro de Barbearia
**POST** `/api/tenants/register`  
*(Requer autenticação Bearer JWT)*

Registra o tenant vinculado ao usuário autenticado.

#### Request Body (`application/json`)
```json
{
  "companyName": "Barbearia Navalha de Ouro",
  "openingHours": "08:00:00",
  "closingHours": "20:00:00",
  "slug": "navalha-de-ouro",
  "whatsapp": "(11) 99999-8888",
  "companyAddress": "Rua Augusta, 1500 - São Paulo/SP"
}
```

**Regras de Validação:**
- `companyName`: Obrigatório, 3 a 100 caracteres.
- `openingHours` / `closingHours`: Obrigatórios, formato de hora (`HH:mm:ss`).
- `slug`: Obrigatório, 3 a 100 caracteres, único no sistema.
- `whatsapp`: Obrigatório, formato de telefone com DDD válido, único no sistema.
- `companyAddress`: Obrigatório, 3 a 255 caracteres.

#### Respostas
- **201 Created**:
  ```json
  {
    "status": 201,
    "message": "Tenant registered successfully",
    "data": null,
    "errors": null
  }
  ```
- **400 Bad Request**: Dados inválidos ou slug/whatsapp já em uso.
- **403 Forbidden**: Token ausente ou expirado.

---

### 2.2 Obter Dados da Barbearia do Usuário Logado
**GET** `/api/tenants/me`  
*(Requer autenticação Bearer JWT)*

#### Respostas
- **200 OK**:
  ```json
  {
    "status": 200,
    "message": "Dados da sua barbearia",
    "data": {
      "companyName": "Barbearia Navalha de Ouro",
      "slug": "navalha-de-ouro",
      "openingHours": "08:00:00",
      "closingHours": "20:00:00",
      "whatsapp": "(11) 99999-8888",
      "companyAddress": "Rua Augusta, 1500 - São Paulo/SP"
    },
    "errors": null
  }
  ```
- **404 Not Found**: Usuário não possui barbearia cadastrada.
- **403 Forbidden**: Não autenticado.

---

### 2.3 Obter Dados Públicos da Barbearia pelo Slug
**GET** `/api/tenants/{slug}`  
*(Acesso Público)*

#### Parâmetros de Rota:
- `slug` (string, obrigatório): Identificador amigável da barbearia (ex: `navalha-de-ouro`).

#### Respostas
- **200 OK**:
  ```json
  {
    "status": 200,
    "message": "Barbearia encontrada com sucesso",
    "data": {
      "companyName": "Barbearia Navalha de Ouro",
      "slug": "navalha-de-ouro",
      "openingHours": "08:00:00",
      "closingHours": "20:00:00",
      "whatsapp": "(11) 99999-8888",
      "companyAddress": "Rua Augusta, 1500 - São Paulo/SP"
    },
    "errors": null
  }
  ```
- **404 Not Found**: Barbearia não encontrada.

---

## 3. Módulo: Tratamentos & Serviços (`/api/tenants/{slug}/treatments`)

### 3.1 Cadastrar Novo Serviço
**POST** `/api/tenants/{slug}/treatments`  
*(Requer autenticação Bearer JWT do dono da barbearia)*

#### Request Body (`application/json`)
```json
{
  "title": "Corte Masculino Degradê",
  "description": "Corte moderno com tesoura e máquina, finalizado com lavagem.",
  "price": 45.00,
  "durationMinutes": 35
}
```

**Regras de Validação:**
- `title`: Obrigatório, 3 a 30 caracteres.
- `description`: Opcional, 10 a 255 caracteres se fornecido.
- `price`: Obrigatório, valor decimal (ex: `45.00`).
- `durationMinutes`: Obrigatório, tempo de execução em minutos inteiros (ex: `35`).

#### Respostas
- **201 Created**:
  ```json
  {
    "status": 201,
    "message": "Serviço criado com sucesso",
    "data": null,
    "errors": null
  }
  ```
- **400 Bad Request**: Validação falhou, barbearia não encontrada ou usuário não é o dono do tenant.
- **403 Forbidden**: Token ausente ou inválido.

---

### 3.2 Listar Serviços da Barbearia
**GET** `/api/tenants/{slug}/treatments`  
*(Acesso Público)*

#### Parâmetros de Rota:
- `slug` (string, obrigatório): Slug da barbearia.

#### Respostas
- **200 OK**:
  ```json
  {
    "status": 200,
    "message": "Serviço encontrado com sucesso",
    "data": [
      {
        "id": "b2d3e4f5-6a7b-8c9d-0e1f-2a3b4c5d6e7f",
        "title": "Corte Masculino Degradê",
        "description": "Corte moderno com tesoura e máquina, finalizado com lavagem.",
        "price": 45.00,
        "durationMinutes": 35
      }
    ],
    "errors": null
  }
  ```
- **400 Bad Request**: Barbearia não encontrada.

