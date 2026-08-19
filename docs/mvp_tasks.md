# Detalhamento de Tarefas Técnicas (MVP)

Este documento contém a quebra das Histórias de Usuário do MVP em Tarefas Técnicas (Sub-tasks) orientadas ao desenvolvimento.

## 1. Gestão de Acesso Básica (Auth & RBAC)

### US-MVP.1: Autenticação Essencial (E-mail e Senha)
> *Como usuário (cliente, barbeiro ou admin), quero criar minha conta e fazer login usando meu e-mail e senha, para ter uma identificação segura e acesso ao sistema.*

**Tarefas Técnicas:**
*   `[Task-1.1]` (Banco de Dados): Modelar e criar a entidade/tabela `users` com os campos `id`, `name`, `email`, `whatsapp`, `password` e `role`.
*   `[Task-1.2]` (Backend): Implementar o endpoint de cadastro `POST /api/auth/register` com validação de DTO, hash de senha e atribuição da role apropriada.
*   `[Task-1.3]` (Backend): Implementar o endpoint de login `POST /api/auth/login` com validação de credenciais e geração de token JWT.
*   `[Task-1.4]` (Frontend): Criar a página e formulário de cadastro, integrando com a API e tratando erros.
*   `[Task-1.5]` (Frontend): Criar a página e formulário de login, armazenando o JWT de forma segura e redirecionando conforme a role.

## 2. Onboarding e Catálogo do Estabelecimento (Tenant)

### US-MVP.2: Criação da Barbearia (Tenant)
> *Como administrador (Tenant Admin), quero registrar minha barbearia informando os dados básicos e recebendo um link público, para que meus clientes me encontrem na plataforma.*

**Tarefas Técnicas:**
*   `[Task-2.1]` (Banco de Dados): Criar a tabela `tenants` com os campos `id`, `name` (Nome Fantasia), `slug`, `logo_url`, `address` e vínculo com o usuário dono (`owner_id`).
*   `[Task-2.2]` (Backend): Criar endpoint `POST /api/tenants` para cadastro da barbearia, validando a unicidade do `slug`.
*   `[Task-2.3]` (Backend): Criar endpoint `GET /api/tenants/{slug}` para busca pública dos dados da barbearia.
*   `[Task-2.4]` (Frontend): Criar tela de onboarding para o Tenant Admin inserir dados da barbearia.
*   `[Task-2.5]` (Frontend): Criar página pública da barbearia acessível via `/{slug}` que consome os dados do tenant.

### US-MVP.3: Cadastro de Serviços Simples
> *Como administrador, quero cadastrar os serviços da minha barbearia informando nome, preço e duração, para exibi-los na minha página pública.*

**Tarefas Técnicas:**
*   `[Task-3.1]` (Banco de Dados): Criar tabela `services` vinculada ao tenant, com `id`, `tenant_id`, `name`, `description`, `price`, `duration` e `active`.
*   `[Task-3.2]` (Backend): Criar CRUD de serviços `GET`, `POST`, `PUT`, `DELETE` em `/api/tenants/{id}/services` (apenas para o admin do tenant).
*   `[Task-3.3]` (Backend): Atualizar o endpoint público `GET /api/tenants/{slug}` para incluir a lista de serviços ativos.
*   `[Task-3.4]` (Frontend): Criar tela no painel do administrador para listagem, criação e edição de serviços.
*   `[Task-3.5]` (Frontend): Atualizar a página pública `/{slug}` para exibir a lista (catálogo) de serviços disponíveis.

## 3. Configuração da Agenda (Profissionais)

### US-MVP.4: Perfil e Vínculo do Profissional
> *Como administrador, quero cadastrar meus barbeiros e vincular quais serviços do catálogo cada um executa, para o motor de busca focar no barbeiro certo.*

**Tarefas Técnicas:**
*   `[Task-4.1]` (Banco de Dados): Criar tabela `professionals` (vinculada ao tenant e opcionalmente ao `users` se tiverem login) e tabela de relacionamento `professional_services`.
*   `[Task-4.2]` (Backend): Implementar CRUD de profissionais no backend `/api/tenants/{id}/professionals`.
*   `[Task-4.3]` (Backend): Implementar endpoint para vincular/desvincular serviços a um profissional.
*   `[Task-4.4]` (Frontend): Criar tela no painel do administrador para gerenciar a equipe (adicionar barbeiros).
*   `[Task-4.5]` (Frontend): Criar modal/tela de seleção de serviços habilitados para cada profissional.

### US-MVP.5: Grade de Horário Semanal do Barbeiro
> *Como administrador, quero definir a grade semanal de cada barbeiro (jornada de trabalho e almoço), para basear a disponibilidade de slots.*

**Tarefas Técnicas:**
*   `[Task-5.1]` (Banco de Dados): Criar tabela `work_schedules` vinculada ao profissional, com `day_of_week`, `start_time`, `end_time`, `break_start`, `break_end`.
*   `[Task-5.2]` (Backend): Implementar endpoints para configurar e buscar a grade de horário de um profissional.
*   `[Task-5.3]` (Frontend): Criar tela no painel do administrador para configuração da jornada de trabalho de cada barbeiro.

## 4. Agendamento de Horários (Core do Cliente Final)

### US-MVP.6: Acesso ao Catálogo e Multi-Serviços
> *Como cliente final, quero acessar a página da barbearia pelo link (/{slug}) e interagir com os serviços, podendo selecionar mais de um na mesma sessão.*

**Tarefas Técnicas:**
*   `[Task-6.1]` (Frontend): Implementar estado global ou contexto no frontend para armazenar os serviços selecionados pelo cliente na sessão.
*   `[Task-6.2]` (Frontend): Atualizar UI da página pública para permitir a seleção múltipla de serviços, exibindo a soma de valores (R$) e duração total dinamicamente.

### US-MVP.7: Seleção de Profissional e Disponibilidade
> *Como cliente final, quero selecionar o barbeiro e ver os horários livres exatos cruzados com os bloqueios, para não correr risco de choque.*

**Tarefas Técnicas:**
*   `[Task-7.1]` (Backend): Criar endpoint `GET /api/scheduling/availability` que recebe tenant, serviços (para calcular duração) e profissional, retornando os slots livres.
*   `[Task-7.2]` (Backend): Desenvolver a lógica (motor) que cruza a `work_schedule`, os `appointments` já marcados no dia e a duração total, devolvendo apenas slots disponíveis.
*   `[Task-7.3]` (Frontend): Criar UI para seleção do profissional na etapa de agendamento (filtrando apenas os que fazem os serviços escolhidos).
*   `[Task-7.4]` (Frontend): Integrar o calendário/seletor de data e hora com o endpoint de disponibilidade.

### US-MVP.8: Confirmação e Reserva
> *Como cliente final, quero revisar as opções na tela de resumo, autenticar-me e confirmar a reserva atômica do meu horário.*

**Tarefas Técnicas:**
*   `[Task-8.1]` (Banco de Dados): Criar tabela `appointments` com `tenant_id`, `customer_id`, `professional_id`, `start_time`, `end_time`, `total_price`, `status`. Criar tabela `appointment_services`.
*   `[Task-8.2]` (Backend): Criar endpoint `POST /api/appointments` para registrar a reserva, validando novamente se o slot ainda está livre (concorrência).
*   `[Task-8.3]` (Frontend): Criar tela de resumo do agendamento (Checkout visual).
*   `[Task-8.4]` (Frontend): Implementar fluxo de redirecionamento para login/cadastro caso o cliente não esteja autenticado, retornando ao resumo em seguida.
*   `[Task-8.5]` (Frontend): Realizar chamada à API para confirmar a reserva e exibir tela de sucesso.

## 5. Gerenciamento de Reservas (Painéis Operacionais)

### US-MVP.9: Visão Operacional ("Meus Agendamentos")
> *Como profissional (barbeiro), quero acessar a aba "Meus Agendamentos" para visualizar os atendimentos do meu dia e mudar o status deles.*

**Tarefas Técnicas:**
*   `[Task-9.1]` (Backend): Criar endpoint `GET /api/professionals/me/appointments` para listar os agendamentos do profissional logado, com filtros de data.
*   `[Task-9.2]` (Backend): Criar endpoint `PATCH /api/appointments/{id}/status` para atualizar o status do atendimento (`COMPLETED`, `NO_SHOW`).
*   `[Task-9.3]` (Frontend): Criar painel do barbeiro com visualização em lista/agenda dos atendimentos do dia.
*   `[Task-9.4]` (Frontend): Implementar botões e integração para alterar o status do agendamento.

### US-MVP.10: Área do Cliente e Cancelamento
> *Como cliente final, quero acessar o meu próprio painel para ver meus agendamentos e poder cancelar um caso ocorra um imprevisto.*

**Tarefas Técnicas:**
*   `[Task-10.1]` (Backend): Criar endpoint `GET /api/customers/me/appointments` para listar o histórico e agendamentos futuros do cliente logado.
*   `[Task-10.2]` (Backend): Implementar regra no endpoint de status (ou criar `POST /api/appointments/{id}/cancel`) que valida se o cancelamento respeita a antecedência mínima.
*   `[Task-10.3]` (Frontend): Criar área restrita do cliente (Minhas Reservas).
*   `[Task-10.4]` (Frontend): Adicionar botão de cancelamento para agendamentos futuros e integrar com a API.
