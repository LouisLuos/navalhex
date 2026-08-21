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

## 📅 20/08/2026 - Endpoint de Cadastro com Validação, Criptografia BCrypt e Frontend Angular (Task-1.2 e Task-1.4)

### 🎯 Objetivo do Dia
Implementar o ciclo completo de cadastro de usuários: do backend Spring Boot (DTO com validação, serviço com hash de senha, tratamento global de exceções e CORS) até o frontend Angular moderno (Single Page Application, Reactive Forms, AuthService e estilização com Tailwind CSS).

---

### 🛠️ O que foi feito:

#### 1. Backend (Spring Boot & PostgreSQL)
* **`RegisterDTO.java`:** Record imutável com validações rigorosas do Jakarta Bean Validation:
  * `@NotBlank` e `@Size(min = 3, max = 100)` para o `name`.
  * `@NotBlank` e `@Email` para o `email`.
  * `@NotBlank`, `@Size(min = 8, max = 60)` e `@Pattern` com Regex para senha forte (maiúscula, minúscula, número e caractere especial).
  * `@NotBlank`, `@Size(min = 11, max = 15)` e `@Pattern` para formato de WhatsApp brasileiro com DDD.
  * `@NotNull` para a `role` (`UserRole`).
* **`ApiResponse<T>.java`:** Contrato padronizado de resposta com Generics (`<T>`) e *Static Factory Methods* (`.success()` e `.error()`).
* **`GlobalExceptionHandler.java`:** Interceptador `@RestControllerAdvice` capturando:
  * `MethodArgumentNotValidException` para formatar os erros do Bean Validation em um mapa `{ campo: "mensagem" }`.
  * `IllegalArgumentException` para tratar erros de regras de negócio (ex: e-mail ou WhatsApp duplicados).
* **`UserService.java`:**
  * Injeção de dependências com `@RequiredArgsConstructor`.
  * Verificação de unicidade com `existsByEmail` e `existsByWhatsapp`.
  * Criptografia de senha com BCrypt via `PasswordEncoder`.
  * Instanciação segura da entidade usando o padrão `@Builder`.
* **`UserEntity.java`:** Mapeamento de tipo enum nativo do Postgres via `@JdbcType(PostgreSQLEnumJdbcType.class)`.
* **`SecurityConfig.java`:** Liberação de rotas públicas de auth e configuração de **CORS** (`CorsConfigurationSource`) para `http://localhost:4200`.
* **`AuthController.java`:** Endpoint `POST /api/auth/register` com `@ResponseStatus(HttpStatus.CREATED)` e payload seguro (sem retorno de senhas).

#### 2. Frontend (Angular 22 & Tailwind CSS v4)
* **Scaffold & Configuração:** Projeto SPA criado com `@angular/cli` e `provideHttpClient()` configurado globalmente em `app.config.ts`.
* **Modelos TypeScript:** `user.model.ts` e `api-response.model.ts` espelhando os contratos do backend.
* **`AuthService.ts`:** Serviço com `@Injectable({ providedIn: 'root' })`, injeção com `inject(HttpClient)` e método reativo `register()` retornando `Observable<ApiResponse<void>>`.
* **`RegisterComponent`:**
  * Formulário reativo com `FormGroup`, `FormControl` e `Validators` nativos.
  * Validação cruzada de confirmação de senha (`confirmPassword`).
  * Estados reativos de carregamento e erro com **Signals** (`isLoading`, `errorMessage`).
  * Estilização visual premium com Tailwind CSS v4 (tema escuro, glassmorphism e cores âmbar do design system).
  * Configuração de rota `/register` no `app.routes.ts`.

---

### 💡 Guia de Revisão & Conceitos Fundamentais:

#### 🔒 1. Segurança do Payload de Resposta (Por que nunca devolver senhas?)
- **Regra:** O backend **nunca** deve devolver a senha (mesmo em hash) no JSON de resposta.
- **Solução aplicada:** O `AuthController` retorna `ApiResponse<Void>`, respondendo apenas confirmação de sucesso (`201 Created`) com `data: null`.

#### 🌐 2. O que é CORS e onde ele deve ser configurado?
- **Conceito:** O CORS é uma trava de segurança do **navegador** (não do Angular).
- **Regra:** O backend é o único responsável por declarar quem tem permissão para acessá-lo.
- **Configuração no Spring:** O `SecurityConfig` usa `CorsConfigurationSource` liberando a origem `http://localhost:4200`, métodos (`GET, POST, PUT, DELETE, OPTIONS, PATCH`) e cabeçalhos.

#### ⚡ 3. Angular Moderno: Injeção de Dependências e Observables
- **`providedIn: 'root'`:** Cria um Singleton global na aplicação sem precisar declarar manualmente no `app.config.ts`.
- **`inject(HttpClient)`:** Função moderna do Angular que substitui a injeção tradicional por construtor.
- **Observables vs Promises:** O `HttpClient` do Angular retorna Observables (RxJS). Eles são *lazy* (só disparam a requisição HTTP quando você chama `.subscribe()`).

#### 📝 4. Reactive Forms (`FormGroup` vs `FormControl`)
- `FormGroup` unifica os campos sob um único objeto, facilitando validações de estado global (`form.valid`), validação cruzada entre campos e envio direto dos valores.

---

### ⏭️ Próximos Passos (Próxima Sessão):
* Implementação da **`[Task-1.3]` (Backend Login)**:
  * Criação do `LoginDTO`.
  * Validação de credenciais e geração de Token JWT (`io.jsonwebtoken` / Spring Security).
  * Retorno do token e claims de role.
* Implementação da **`[Task-1.5]` (Frontend Login)**:
  * Criação do `LoginComponent`.
  * Armazenamento seguro do JWT e gerenciamento de estado de sessão.
