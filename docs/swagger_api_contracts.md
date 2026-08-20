# Contratos da API (Swagger/OpenAPI Docs)

Este documento centraliza o design e os contratos da API do Navalhex. No futuro, isso pode ser gerado automaticamente pelo Swagger/Springdoc, mas por enquanto mantemos o registro da modelagem aqui.

---

## Módulo: Auth & Users

### Cadastro de Usuário
**POST** `/api/auth/register`

Registra um novo usuário no sistema.

#### Request Body
`application/json`

```json
{
  "name": "João da Silva",
  "email": "joao@email.com",
  "password": "Password123@",
  "whatsapp": "(11) 91234-5678"
}
```

**Regras de Validação:**
- `name`: Obrigatório, mínimo 3 e máximo 100 caracteres.
- `email`: Obrigatório, deve ter formato de e-mail válido.
- `password`: Obrigatório, mínimo 8 e máximo 60 caracteres. Deve conter no mínimo: 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial.
- `whatsapp`: Obrigatório, mínimo 11 e máximo 15 caracteres. Formato validado por Regex para o padrão brasileiro com DDD.

#### Respostas

**201 Created**
(Conteúdo da resposta de sucesso a ser definido)

**400 Bad Request**
Retornado quando alguma das validações do DTO falha.
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Sua senha deve conter pelo menos 8 caracteres...",
  "path": "/api/auth/register"
}
```
