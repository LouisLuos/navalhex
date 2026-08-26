# 🗺️ Trilha de Estudos & Desenvolvimento — Navalhex (Java 21 + Angular)

Este roadmap organiza o aprendizado teórico e prático para o desenvolvimento do **Navalhex**, dividindo os conceitos em marcos progressivos (*checkpoints*). Marque com `[x]` conforme for dominando e aplicando.

---

## ☕ Bloco 1: Backend — Java 21 & Spring Boot Core

### 1.1 Ciclo de Vida da Requisição & Camadas
- [ ] Entender a cadeia de responsabilidade real:
  - **Filtros (Security/CORS)** ➔ **Controller (DTOs/@Valid)** ➔ **Service (Regras de Negócio)** ➔ **Repository (Acesso a Dados)** ➔ **Banco**.
- [ ] Uso correto de DTOs (*Data Transfer Objects*) para isolar entidades do banco da API externa.
- [ ] Validações de entrada com Bean Validation (`@NotNull`, `@NotBlank`, `@Size`, `@Pattern`, `@Valid`).
- [ ] Tratamento global de exceções com `@RestControllerAdvice` e `@ExceptionHandler` (padronização de erros HTTP 400, 404, 409, 500).

### 1.2 Persistência de Dados com Spring Data JPA & PostgreSQL
- [ ] Modelagem de Entidades (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`).
- [ ] Mapeamento de Relacionamentos:
  - `@OneToMany`, `@ManyToOne`, `@ManyToMany`.
  - Cuidados com relacionamentos bidirecionais e loops no JSON (`@JsonIgnore`, DTOs).
- [ ] Tipos de Carregamento: `FetchType.LAZY` vs `FetchType.EAGER` e o problema do **N+1**.
- [ ] Criação de queries customizadas com `@Query` (JPQL e SQL Nativo) e *Derived Query Methods*.
- [ ] Gerenciamento de Transações com `@Transactional` (quando abrir, quando fazer rollback, `readOnly = true`).
- [ ] Versionamento e migração de banco com **Flyway** (`V1__...sql`).

### 1.3 Segurança & Autenticação (Spring Security 6 + JWT)
- [ ] Entender a arquitetura do Spring Security:
  - O que é o `SecurityFilterChain` e como funciona a cadeia de filtros.
- [ ] Criação de filtro customizado (`OncePerRequestFilter`) para interceptar o header `Authorization: Bearer <token>`.
- [ ] Geração, assinatura e validação de tokens JWT (Payload, Claims, Expiração, Chave Secreta/Par de Chaves).
- [ ] Controle de Acesso Baseado em Perfis (RBAC):
  - Definição de `GrantedAuthority` / `Role` (`ADMIN`, `TENANT`, `BARBER`, `CUSTOMER`).
  - Proteção de endpoints por rota (`requestMatchers`) e por método (`@PreAuthorize`).
- [ ] Configuração correta de CORS (*Cross-Origin Resource Sharing*) para comunicação com o Angular.

### 1.4 Lógica de Negócio Crítica do Navalhex
- [ ] Modelagem Multi-Tenant: isolamento dos dados de cada barbearia.
- [ ] Motor de Disponibilidade:
  - Cálculo de slots livres cruzando jornada de trabalho (`work_schedules`) e agendamentos existentes (`appointments`).
  - Soma dinâmica de múltiplos serviços selecionados (tempo total e valor total).
- [ ] Controle de Concorrência & Overbooking:
  - Bloqueio Otimista (`@Version`) vs Bloqueio Pessimista (`@Lock(LockModeType.PESSIMISTIC_WRITE)`).
  - Como garantir reservas atômicas sem colisão de horários simultâneos.

---

## 🅰️ Bloco 2: Frontend — Angular Moderno

### 2.1 Fundamentos & Estrutura de Componentes
- [ ] Arquitetura Standalone Components (sem `NgModule`).
- [ ] Sintaxe moderna de templates Control Flow (`@if`, `@for`, `@switch`).
- [ ] Comunicação entre Componentes:
  - Entradas (`input()` / `@Input()`) e Saídas (`output()` / `@Output()`).
  - Compartilhamento de estado via Serviços.
- [ ] Ciclo de Vida do Componente (`ngOnInit`, `ngOnDestroy`, `afterRender`, etc.).

### 2.2 Reatividade: RxJS & Signals
- [ ] Compreender a diferença e o uso combinado de:
  - **Signals** (`signal()`, `computed()`, `effect()`) para estado reativo da tela.
  - **RxJS Observables** (`HttpClient`, pipes, operadores) para fluxos assíncronos e eventos.
- [ ] Operadores RxJS essenciais: `map`, `switchMap`, `catchError`, `tap`, `finalize`.
- [ ] Evitar Memory Leaks (uso de `takeUntilDestroyed` ou `toSignal`).

### 2.3 Roteamento & Proteção de Rotas
- [ ] Configuração de rotas estáticas e rotas dinâmicas (`/:slug/agendamento`).
- [ ] Carregamento sob demanda (*Lazy Loading*) de páginas/módulos.
- [ ] Guardas de Rota (*Route Guards* funcionais):
  - `CanActivateFn` para barrar usuários não logados.
  - Guardas por Role (impedir Cliente de acessar tela de Barbeiro/Admin).

### 2.4 Formulários & Validações
- [ ] **Reactive Forms** (`FormGroup`, `FormControl`, `FormBuilder`, `Validators`).
- [ ] Validações síncronas customizadas e validações assíncronas.
- [ ] Feedback visual de erros de formulário em tempo real.

### 2.5 Comunicação HTTP & Autenticação no Front
- [ ] Consumo de APIs REST com `HttpClient`.
- [ ] Criação de `HttpInterceptorFn`:
  - Anexar token JWT automaticamente em todas as requisições autenticadas.
  - Interceptar erros 401/403 e redirecionar para o login.
- [ ] Serviço de Autenticação (`AuthService`):
  - Armazenamento seguro de sessão (LocalStorage/Cookies).
  - Estado do usuário autenticado exposto via Signal.

---

## 🔌 Bloco 3: Integração & Telas do Navalhex

### 3.1 Fluxo Público do Cliente
- [ ] Tela da Barbearia (`/{slug}`): listagem do catálogo de serviços e equipe.
- [ ] Fluxo de Agendamento:
  - Seleção de múltiplos serviços.
  - Seleção de profissional e calendário dinâmico de datas/horários disponíveis.
  - Confirmação e identificação/cadastro rápido do cliente.

### 3.2 Painel Operacional do Barbeiro
- [ ] Visualização da agenda diária/semanal em formato de timeline ou cartões.
- [ ] Ações rápidas: marcar como concluído, cancelar, reagendar.

### 3.3 Painel Administrativo do Tenant
- [ ] Gestão de Serviços (CRUD com preço e tempo estimado).
- [ ] Gestão da Equipe e Jornada de Trabalho (horários de entrada, almoço, saída por dia da semana).
