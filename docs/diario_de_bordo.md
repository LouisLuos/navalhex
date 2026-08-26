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

## 📅 22/08/2026 - Autenticação JWT Stateless, SecurityFilter e Integração de Login no Frontend Angular (Task-1.3 e Task-1.5)

### 🎯 Objetivo do Dia
Implementar o fluxo completo de autenticação e proteção de rotas com JSON Web Tokens (JWT) no Spring Boot e integrar com o Frontend Angular:
1. Geração, assinatura e validação de tokens JWT usando `com.auth0:java-jwt`.
2. Filtro de segurança customizado (`SecurityFilter` herdando de `OncePerRequestFilter`).
3. Endpoint de Login (`POST /api/auth/login`) com mitigação de *User Enumeration*.
4. Frontend Angular com Reactive Forms, `AuthService.login()` com operador `tap`, tela de Login estilizada e página de `Dashboard`.

---

### 🛠️ O que foi feito:

#### 1. Backend (Spring Boot 4 / Java 21)
* **Dependência `java-jwt`:** Adicionada a biblioteca oficial da Auth0 (`com.auth0:java-jwt:4.4.0`) no `pom.xml`.
* **Propriedade Secreta Configurável:** Chave secreta de assinatura externalizada no `application.properties` com fallback para dev:
  `api.security.token.secret=${JWT_SECRET:minha-chave-secreta-super-segura-123456}`.
* **`TokenService.java` (`br.com.navalhex.security`):**
  * `generateToken(UserEntity user)`: Emite token assinado com algoritmo `HMAC256`, expiração de 2 horas (fuso `-03:00`), `subject` com o e-mail e claim personalizado `role`.
  * `validateToken(String token)`: Valida a integridade e assinatura criptográfica do token, retornando o e-mail ou string vazia caso inválido/expirado.
  * `recoverToken(HttpServletRequest request)`: Extrai o token do cabeçalho `Authorization: Bearer <token>`.
* **DTOs de Autenticação (`modules.user.dto`):**
  * **`LoginDTO.java`:** Record imutável com `@NotBlank` e `@Email` para e-mail e `@NotBlank` para senha.
  * **`LoginResponseDTO.java`:** Record devolvendo `token`, `name`, `email` e `role`, com construtor auxiliar de conveniência `LoginResponseDTO(UserEntity, String)`.
* **`UserService.java`:**
  * Método `login(LoginDTO)`:
    * Busca o usuário por e-mail no `UserRepository`.
    * Validação segura de senha com `passwordEncoder.matches(rawPassword, encodedPassword)`.
    * Proteção contra **User Enumeration**: Mesma mensagem genérica (*"Email ou senha inválidos"*) tanto para usuário inexistente quanto para senha incorreta.
* **`SecurityFilter.java` (`OncePerRequestFilter`):**
  * Interceptador de requisições que extrai o token, valida no `TokenService`, recupera o usuário e injeta a autenticação no contexto do Spring Security (`UsernamePasswordAuthenticationToken` + `SecurityContextHolder`).
* **`SecurityConfig.java`:**
  * Configuração de sessão **`SessionCreationPolicy.STATELESS`** (sem criação de cookies de sessão em memória).
  * Registro do filtro customizado na esteira antes do filtro padrão: `.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)`.

#### 2. Frontend (Angular 22 & Tailwind CSS)
* **Modelos TypeScript (`user.model.ts`):** Criados os tipos `LoginDTO` e `LoginResponseDTO`.
* **`AuthService.ts`:**
  * Implementado `login(user: LoginDTO)` utilizando o operador **`tap` do RxJS** para salvar o token no `localStorage` sem consumir o Observable, mantendo o fluxo reativo disponível para o componente.
* **`LoginComponent` (`pages/login`):**
  * Formulário reativo com `FormGroup` e `ReactiveFormsModule`.
  * Estados reativos de `isLoading` e `errorMessage` via Signals.
  * Redirecionamento automático para `/dashboard` em caso de sucesso.
* **`DashboardComponent` (`pages/dashboard`):**
  * Página protegida exibindo status de autenticação e botão de Logout (limpeza de token e redirecionamento para `/login`).
* **Estilização com TailwindCSS:**
  * Interface moderna, estética escura (dark theme) com efeitos de vidro (`backdrop-blur-xl`), glow effects e paleta de cores âmbar/zinc do design system Navalhex.

---

### 💡 Guia de Revisão & Conceitos Fundamentais para Estudar:

#### 1. Java & Spring Security
* **`@Bean` vs `@Component`:**
  * Use `@Component` / `@Service` em classes do seu próprio código.
  * Use `@Bean` em métodos dentro de classes `@Configuration` quando você precisa instanciar, configurar e registrar no container do Spring classes externas ou customizadas (ex: `SecurityFilterChain`, `PasswordEncoder`).
* **JWT Stateless vs Sessão Tradicional:**
  * No JWT, o servidor **não precisa guardar sessões em memória nem consultar tabelas de token no banco** a cada requisição. O próprio token é assinado e autônomo (carrega `subject`, `roles` e `exp`).
* **A Esteira de Segurança (*Filter Chain*) e `OncePerRequestFilter`:**
  * Requisições HTTP passam por uma esteira de filtros em fila. O `filterChain.doFilter(req, res)` passa o controle para o próximo filtro.
  * O `SecurityContextHolder.getContext().setAuthentication(auth)` é o "crachá" que avisa todos os controllers subsequentes que a requisição está autenticada.
* **Mitigação de *User Enumeration*:**
  * Nunca informe ao cliente se o e-mail não existe vs se a senha está errada no login. Responda sempre uma mensagem genérica de erro para não vazar a existência de contas para invasores.

#### 2. Angular & RxJS
* **Operador `tap` vs `.subscribe()` no Service:**
  * **Regra de Ouro:** Nunca dê `.subscribe()` dentro de um Service que precisa ser consumido por um componente. O `.subscribe()` consome o fluxo e retorna uma `Subscription`.
  * Use `.pipe(tap(...))` para efeitos colaterais (como salvar no `localStorage`) e retorne o `Observable` intacto para o componente se inscrever e controlar o estado da tela (loading, erros, redirecionamentos).
* **Observer (`next`, `error`, `complete`):**
  * `next`: chamado quando o dado chega com sucesso (2xx).
  * `error`: chamado quando a requisição falha (4xx/5xx/rede).
  * `complete`: chamado quando o fluxo é finalizado.
* **Formulários Reativos Standalone:**
  * Componentes standalone que usam `[formGroup]` e `formControlName` **precisam** importar o `ReactiveFormsModule` no seu array de `imports`.

---

## 📅 24/08/2026 - Módulo de Barbearia (Tenant), Migration V2 e Endpoint de Cadastro (Task-2.1 e Task-2.2)

### 🎯 Objetivo do Dia
Iniciar a **US-MVP.2 (Criação da Barbearia/Tenant)** implementando a persistência no banco de dados e a camada backend para cadastro do estabelecimento com vínculo de dono (`owner_id`):
1. Criação da migration `V2__init_table_tenants.sql`.
2. Criação do módulo `br.com.navalhex.modules.tenants` com Entity, Repository, DTO, Service e Controller.
3. Validação de unicidade de `slug` e regra de 1 barbearia por dono logado (`@AuthenticationPrincipal`).

---

### 🛠️ O que foi feito:

#### 1. Banco de Dados (Flyway Migration)
* **`V2__init_table_tenants.sql`:**
  * Criação da tabela `tenants` com:
    * Chave primária `id UUID DEFAULT gen_random_uuid()`.
    * Foreign key `owner_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE`.
    * `company_name VARCHAR(150) NOT NULL`.
    * Horários de funcionamento `opening_hours TIME NOT NULL` e `closing_hours TIME NOT NULL`.
    * `slug VARCHAR(100) NOT NULL UNIQUE` para link público.
    * `whatsapp VARCHAR(20) NOT NULL` e `company_address VARCHAR(255) NOT NULL`.
    * Campos de auditoria `created_at` e `updated_at`.

#### 2. Backend (Spring Boot 4 / Java 21)
* **`TenantEntity.java`:** Mapeamento JPA com Lombok (`@Builder`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) e auditoria via `@CreationTimestamp` e `@UpdateTimestamp`.
* **`RegisterDTO.java` (`modules.tenants.dto`):** Record de entrada com validações do Jakarta Bean Validation (`@NotBlank`, `@NotNull`, `@Size`, `@Pattern`).
* **`TenantResponseDTO.java`:** Record público de resposta para o cliente.
* **`TenantRepository.java`:** Interface JPA com derived query methods `existsBySlug`, `existsByOwnerId`, `findBySlug` e `findByOwnerId`.
* **`TenantService.java`:**
  * Validação de unicidade do `slug`.
  * Validação de que o usuário logado ainda não possui barbearia cadastrada (`existsByOwnerId`).
  * `getMyTenant(UUID ownerId)` para carregar a barbearia do usuário logado.
  * `getTenantBySlug(String slug)` para consulta pública.
* **`TenantController.java`:**
  * Endpoint `POST /api/tenants/register` anotado com `@ResponseStatus(HttpStatus.CREATED)`.
  * Endpoint `GET /api/tenants/me` para consulta autenticada do tenant do usuário.
  * Endpoint `GET /api/tenants/{slug}` para consulta pública de dados da barbearia.

#### 3. Frontend (Angular 22 & Tailwind CSS)
* **`auth.interceptor.ts`:** Functional HttpInterceptor que anexa automaticamente o cabeçalho `Authorization: Bearer <token>` em todas as requisições HTTP do Angular.
* **`tenant.ts` (Service):** Métodos `registerTenant`, `getMyTenant` e `getTenantBySlug` consumindo a API com `ApiResponse<T>`.
* **`OnboardingComponent` (`pages/onboarding`):**
  * Formulário reativo para cadastro do estabelecimento.
  * Geração dinâmica de slug a partir do nome fantasia digitado.
* **`PublicTenantComponent` (`pages/public-tenant`):**
  * Landing page pública acessível via `/{slug}` exibindo dados da barbearia, horário de funcionamento e botão para WhatsApp.
* **`LoginComponent` Inteligente:**
  * Checagem pós-login para a role `TENANT`: se não tiver barbearia, redireciona para `/onboarding`; se já tiver, vai para `/dashboard`.

---

### 💡 Conceitos e Decisões Aprendidos:
* **Flyway Incremental:** Arquivos de migration adicionais (`V2`, `V3`, etc.) devem conter apenas novas tabelas/alterações, sem redeclarar estruturas criadas no `V1`.
* **Injeção de Usuário Logado com `@AuthenticationPrincipal`:** Como o Spring Security injeta o `UserEntity` autenticado diretamente nos parâmetros do Controller sem acoplar o Service com outros módulos.
* **HTTP Interceptors no Angular (`HttpInterceptorFn`):** Centralização do envio do token JWT em um único ponto, eliminando código duplicado nos serviços.
* **Roteamento Estático vs Dinâmico no Spring MVC:** Rotas fixas como `/me` devem sempre vir antes de rotas com variáveis de path dinâmicas como `/{slug}` para evitar conflitos de captura.

---

### ⏭️ Próximos Passos:
1. **US-MVP.3: Cadastro de Serviços Simples:**
   - Criação da tabela `treatments` vinculada ao `tenant_id` (`Task-3.1`) - **[CONCLUÍDO]**
   - Endpoints `POST` e `GET` de tratamentos no Backend (`Task-3.2`) - **[CONCLUÍDO]**
   - Tela de gestão de serviços no painel do administrador e exibição no catálogo público da barbearia (`Task-3.4` e `Task-3.5`).

---

## 📅 25/08/2026 - Módulo de Serviços/Tratamentos (Treatments), Migration V3 e Endpoints POST & GET (Task-3.1 e Task-3.2)

### 🎯 Objetivo do Dia
Iniciar a **US-MVP.3 (Cadastro de Serviços Simples)** implementando:
1. Migration `V3__init_table_treatments.sql`.
2. Criação do módulo `br.com.navalhex.modules.treatments` com Entity, Repository, DTOs, Service e Controller.
3. Validação de autorização multi-tenant (garantir que apenas o dono do tenant cadastre serviços).
4. Endpoint público para listar todos os tratamentos por `slug`.
5. Atualização da especificação OpenAPI (`docs/swagger.yaml`) e contratos em Markdown (`docs/swagger_api_contracts.md`).

---

### 🛠️ O que foi feito:

#### 1. Banco de Dados (Flyway Migration)
* **`V3__init_table_treatments.sql`:**
  * Tabela `treatments` com `id UUID PRIMARY KEY DEFAULT gen_random_uuid()`.
  * `title VARCHAR(30) NOT NULL` e `description VARCHAR(255)`.
  * `price DECIMAL(10, 2) NOT NULL` e `duration_minutes INT NOT NULL`.
  * Foreign key `tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE`.
  * Auditoria com `created_at` e `updated_at`.

#### 2. Backend (Spring Boot 4 / Java 21)
* **`TreatmentsEntity.java`:** Entidade JPA com Lombok e campos mapeados (`id`, `title`, `description`, `price`, `duration` via `duration_minutes`, `tenantId`).
* **`TreatmentsDTO.java`:** Record imutável com Bean Validation (`@NotBlank`, `@Size`, `@NotNull`).
* **`ResponseTreatmentsDTO.java`:** DTO de resposta expondo `id`, `title`, `description`, `price`, `durationMinutes`.
* **`TreatmentsRepository.java`:** Derived query method `List<TreatmentsEntity> findByTenantId(UUID tenantId)`.
* **`TreatmentsService.java`:**
  * `createTreatment`: Busca o tenant por slug, valida se `user.getId().equals(tenant.getOwnerId())`, monta a entidade e persiste.
  * `getTreatments`: Busca o tenant por slug e lista todos os tratamentos via `findByTenantId(tenant.getId())` mapeando via Stream API.
* **`TreatmentsController.java`:**
  * `POST /api/tenants/{slug}/treatments` (Autenticado via `@AuthenticationPrincipal UserEntity user`, HTTP 201).
  * `GET /api/tenants/{slug}/treatments` (Público, HTTP 200).

---

### 💡 Guia de Estudos: JPA Derived Queries vs Relacionamentos de Entidade (@OneToMany)

#### Cenário 1: Consulta Direta por Repository (O que fizemos hoje)
* **Como funciona:**
  - As entidades `TenantEntity` e `TreatmentsEntity` são desacopladas (guardam apenas o `UUID tenantId` puro).
  - No Service, buscamos o tenant e depois chamamos `treatmentsRepository.findByTenantId(tenant.getId())`.
* **Vantagens:**
  - **Simplicidade e Desacoplamento:** Fácil de entender, sem risco de carregar dados pesados sem querer.
  - **Evita o problema N+1:** Você tem controle total e explícito de cada `SELECT` enviado ao banco.
  - **Ideal para Arquiteturas Modulares:** Um módulo não precisa carregar o grafo inteiro de objetos de outro módulo.
* **Desvantagens:**
  - Exige chamar dois repositórios explicitamente quando precisamos das duas entidades.

#### Cenário 2: Relacionamento Mapeado com `@OneToMany`
* **Como funcionaria:**
  - Dentro de `TenantEntity`, colocaríamos:
    ```java
    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<TreatmentsEntity> treatments = new ArrayList<>();
    ```
  - E dentro de `TreatmentsEntity`:
    ```java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
    ```
* **Como consultaríamos:** `tenant.getTreatments()` carregaria os serviços.
* **Cuidado com Armadilhas do `@OneToMany`:**
  - **LazyInitializationException:** Se tentar acessar `tenant.getTreatments()` fora de uma transação aberta (`@Transactional`), o Hibernate estoura erro.
  - **Problema de Serialização JSON / Loop Infinito:** Se retornar a entidade diretamente no Controller, o Jackson tenta serializar `Tenant -> Treatments -> Tenant -> Treatments...` até dar StackOverflow. (Por isso **SEMPRE** usamos DTOs!).

---

## 📅 26/08/2026 - Conclusão do Frontend da Task 3, Refatoração do Design System & Suporte a Light/Dark Theme

### 🎯 Objetivo do Dia
1. Concluir a **US-MVP.3 (Cadastro e Catálogo de Serviços no Frontend)**:
   - Criação de `treatment.model.ts` e `treatment.service.ts` com métodos `getTreatments`, `registerTreatment`, `updateTreatment` e `deleteTreatment`.
   - Implementação do painel de **"Catálogo de Serviços"** no Dashboard com listagem em cards, modal para novo/edição e ação de exclusão.
   - Atualização da **Página Pública (`/{slug}`)** renderizando os serviços reais com preço e duração.
2. **Refatoração do Design System & Suporte a Light Theme:**
   - Eliminação de gradientes e efeitos de brilho laranja pesados em favor de um padrão sóbrio, limpo e direto ao ponto (estilo Linear/Shadcn).
   - Criação do `ThemeService` no Angular com persistência no `localStorage`.
   - Suporte completo a **Light Mode** e **Dark Mode** em todas as telas com botão de alternância (☀️ / 🌙).

---

### 🛠️ O que foi feito:

#### 1. Frontend (Angular & Tailwind CSS)
* **`treatment.model.ts`:** Tipos TypeScript sincronizados com os contratos do backend (`title`, `description`, `price`, `durationMinutes`).
* **`treatment.service.ts`:** Consumo das rotas dinâmicas `/api/tenants/{slug}/treatments`.
* **`theme.service.ts`:** Gerenciador de tema reativo via Signals, manipulando a classe `.dark` no elemento raiz `<html>` e persistindo no `localStorage`.
* **`DashboardComponent`:**
  - Métricas rápidas da unidade (serviços ativos, jornada e slug).
  - Tabela/Cards do catálogo com preço em R$ e badge de duração.
  - Modal interativo para criação e edição com validação reativa.
  - Botão de alternar tema e copiar link público.
* **`PublicTenantComponent`:**
  - Exibição limpa do catálogo de serviços para clientes.
  - Alternador de tema no cabeçalho público.
* **Telas de Login, Registro e Onboarding:**
  - Refatoradas com a nova paleta neutra e suporte dual Light & Dark.

---

### 💡 Conceitos e Decisões Aprendidos:
* **Tailwind v4 `@custom-variant dark`:** Configuração do seletor `.dark` para suporte a alternância manual de temas independentemente do sistema operacional.
* **Alinhamento Rigoroso de Contratos (DTOs):** A importância de manter a nomenclatura exata dos campos no frontend (`title`, `durationMinutes`) em sincronia com o backend Java para evitar `null` no banco de dados.
* **Design Funcional vs Decorativo:** Como um design limpo e com alto contraste melhora a usabilidade e reduz o ruído visual em aplicações de gestão e agendamento.

---

### ⏭️ Próximos Passos (Próxima Sessão):
1. **US-MVP.4: Perfil e Vínculo do Profissional (Task 4):**
   - Migration `V4__init_table_professionals.sql` e tabela NxN `professional_treatments`.
   - Módulo Spring Boot `professionals` com CRUD e endpoint de associação de serviços.





