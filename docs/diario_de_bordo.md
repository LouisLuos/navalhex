# Diário de Desenvolvimento - Navalhex

Este documento mapeia o histórico diário de progresso técnico, decisões arquiteturais e tarefas concluídas durante o desenvolvimento do Navalhex.

---

## 📅 19/08/2026 - Configuração de Infraestrutura, Migração Flyway e Modelagem de Usuários (Task-1.1)

### 🎯 Objetivo do Dia
Configurar o ambiente de banco de dados local com Docker Compose, alinhar as propriedades do Spring Boot e concluir a **`[Task-1.1]`** (Modelagem e criação da entidade/tabela `users`).

---

### 🛠️ O que foi feito:

#### 1. Infraestrutura & Banco de Dados Local
* **Docker Compose:** Configuração e inicialização do container PostgreSQL 16 Alpine (`navalhex-postgres`) com volume persistente (`pgdata`) na porta `5432`.
* **Spring Boot Properties:** Alinhamento das credenciais de conexão do `application.properties` com o banco local.

#### 2. Migração com Flyway
* **Script `V1__init_table_users.sql`:**
  * Criação do tipo `ENUM` nativo do PostgreSQL: `user_role ('ADMIN', 'TENANT', 'BARBER', 'CUSTOMER')`.
  * Criação da tabela `users` com:
    * Chave Primária com `UUID` e geração via `DEFAULT gen_random_uuid()`.
    * Constraints de unicidade e não-nulidade para `email` e `whatsapp`.
    * Limitação de tamanho de senha para hash BCrypt (`VARCHAR(60)`).
    * Campos de auditoria `created_at` e `updated_at` com default `NOW()`.

#### 3. Arquitetura Modular & Mapeamento JPA
* Decisão arquitetural de organização **Modular (Package-by-feature)**:
  * Criado o módulo `br.com.navalhex.modules.user`.
* **`UserRole.java` (`modules.user.entity`):** Enum Java espelhando os perfis de acesso do banco.
* **`UserEntity.java` (`modules.user.entity`):**
  * Mapeamento completo JPA da tabela `users`.
  * Configuração do Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`).
  * Mapeamento da `role` como `@Enumerated(EnumType.STRING)`.
  * Auditoria automática via Hibernate (`@CreationTimestamp` e `@UpdateTimestamp`).

#### 4. Camada de Persistência (Repository)
* **`UserRepository.java` (`modules.user.repository`):**
  * Herança de `JpaRepository<UserEntity, UUID>`.
  * Declaração de *Derived Query Methods*:
    * `findByEmail(String email)` retornando `Optional<UserEntity>`.
    * `findByWhatsapp(String whatsapp)` retornando `Optional<UserEntity>`.
    * `existsByEmail(String email)` e `existsByWhatsapp(String whatsapp)` para validações rápidas.

---

### 💡 Conceitos e Decisões Aprendidos:
* **UUID vs Long:** Por que usar UUID em SaaS (segurança, opacidade e descentralização) mesmo com pequeno custo de bytes.
* **Flyway vs Hibernate ddl-auto:** A importância do controle estrito e versionado de migrações SQL em projetos profissionais.
* **`Optional<T>`:** Como a "caixa" do Java evita `NullPointerException` e força o tratamento de registros inexistentes.
* **Herança do `JpaRepository`:** Entendimento de quais métodos já vêm prontos por herança e como funcionam as consultas automáticas por nome de método.

---

### ⏭️ Próximos Passos (Próxima Sessão):
* Início da **`[Task-1.2]`**:
  * Criação dos DTOs de cadastro (`UserRegisterRequestDTO` e `UserResponseDTO`).
  * Implementação da camada de serviço (`UserService`) com hash de senha (BCrypt) e regras de validação.
  * Implementação do endpoint `POST /api/auth/register` no `AuthController`.
