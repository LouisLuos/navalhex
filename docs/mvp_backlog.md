# Produto Mínimo Viável (MVP) - Backlog Detalhado (INVEST)

O objetivo deste MVP é focar exclusivamente no **core de valor** da plataforma: o fluxo de agendamento online de horários sem risco de conflitos. Funcionalidades de escala SaaS (Mercado Pago, Webhooks, Dashboards Globais) e engajamento avançado (WhatsApp, OAuth2, Categorias) foram deixadas para a Fase 2 (Go-to-Market rápido).

Os itens a seguir refletem os **Requisitos Funcionais (RF)** do escopo completo mapeados e adaptados para a primeira versão utilizável da plataforma.

---

## 1. Gestão de Acesso Básica (Auth & RBAC)

**US-MVP.1: Autenticação Essencial (E-mail e Senha)** *(Mapeamento: RF001, RF002)*
**História:** Como usuário (cliente, barbeiro ou admin), quero criar minha conta e fazer login usando meu e-mail e senha, para ter uma identificação segura e acesso ao sistema.
**Critérios de Aceite:**
- **RF001:** O cadastro do cliente exige obrigatoriamente os campos: nome, e-mail, telefone (WhatsApp) e senha.
- **RF002:** O sistema emite um token JWT contendo as claims de perfil (ex: `ROLE_CUSTOMER`, `ROLE_TENANT_ADMIN`, `ROLE_PROFESSIONAL`).
- Tratamento de erro explícito na tela para credenciais incorretas.

---

## 2. Onboarding e Catálogo do Estabelecimento (Tenant)

**US-MVP.2: Criação da Barbearia (Tenant)** *(Mapeamento: RF005, RF009)*
**História:** Como administrador (Tenant Admin), quero registrar minha barbearia informando os dados básicos e recebendo um link público, para que meus clientes me encontrem na plataforma.
**Critérios de Aceite:**
- **RF005:** Formulário solicitando Nome Fantasia e a definição do "slug" da URL (ex: `/navalha-club`). O sistema valida a unicidade da URL.
- **RF009:** Configuração básica do estabelecimento incluindo Logotipo e Endereço Completo.
- *O bypass da integração de pagamento (Mercado Pago - RF006) é aplicado nesta versão (o super admin libera o uso sem checagem financeira).*

**US-MVP.3: Cadastro de Serviços Simples** *(Mapeamento: RF010)*
**História:** Como administrador, quero cadastrar os serviços da minha barbearia informando nome, preço e duração, para exibi-los na minha página pública.
**Critérios de Aceite:**
- **RF010:** O cadastro permite criar, editar e desativar serviços informando: Nome, Descrição opcional, Preço (R$) e Duração estimada (em minutos).
- *Categorização de Serviços (RF011) não será exigida na v1 para manter a usabilidade simples.*

---

## 3. Configuração da Agenda (Profissionais)

**US-MVP.4: Perfil e Vínculo do Profissional** *(Mapeamento: RF012)*
**História:** Como administrador, quero cadastrar meus barbeiros e vincular quais serviços do catálogo cada um executa, para o motor de busca focar no barbeiro certo.
**Critérios de Aceite:**
- **RF012:** Cadastro de membros da equipe informando nome, e-mail e telefone (WhatsApp).
- Seleção dos serviços que aquele profissional está habilitado a executar (checkboxes).

**US-MVP.5: Grade de Horário Semanal do Barbeiro** *(Mapeamento: RF013)*
    **História:** Como administrador, quero definir a grade semanal de cada barbeiro (jornada de trabalho e almoço), para basear a disponibilidade de slots.
    **Critérios de Aceite:**
    - **RF013:** Parametrização da jornada indicando: dias de trabalho da semana, horário de início/fim e um intervalo para almoço/descanso.
    - *A gestão de Bloqueios de Agenda pontuais (RF014) pode ficar de fora da V1, sendo resolvido pelo profissional através de fechamento manual ou cancelamento.*

---

## 4. Agendamento de Horários (Core do Cliente Final)

**US-MVP.6: Acesso ao Catálogo e Multi-Serviços** *(Mapeamento: RF015, RF016)*
**História:** Como cliente final, quero acessar a página da barbearia pelo link (`/{slug}`) e interagir com os serviços, podendo selecionar mais de um na mesma sessão.
**Critérios de Aceite:**
- **RF015:** O cliente consegue visualizar a página e catálogo sem necessidade de login prévio.
- **RF016:** O cliente pode escolher um ou mais serviços; o sistema deve somar as durações em minutos e o valor total (R$) no frontend em tempo real.

**US-MVP.7: Seleção de Profissional e Disponibilidade** *(Mapeamento: RF017, RF018)*
**História:** Como cliente final, quero selecionar o barbeiro e ver os horários livres exatos cruzados com os bloqueios, para não correr risco de choque.
**Critérios de Aceite:**
- **RF017:** A interface permite que o cliente selecione um barbeiro habilitado para os serviços escolhidos.
- **RF018:** O backend cruza a duração total dos serviços selecionados com a jornada (RF013) do profissional e com os agendamentos existentes no dia, exibindo apenas e estritamente os slots contínuos disponíveis reais.

**US-MVP.8: Confirmação e Reserva** *(Mapeamento: RF019)*
**História:** Como cliente final, quero revisar as opções na tela de resumo, autenticar-me e confirmar a reserva atômica do meu horário.
**Critérios de Aceite:**
- **RF019:** Exibir a tela de resumo contendo valor final, data e horário. O usuário autentica-se e confirma o agendamento sem perder a sessão.
- O bloco de horário selecionado deve ficar imediatamente indisponível para futuras requisições ao ser gravado no banco de dados. *(As notificações WhatsApp RF020 são trocadas por validação visual na tela do cliente para o MVP).*

---

## 5. Gerenciamento de Reservas (Painéis Operacionais)

**US-MVP.9: Visão Operacional ("Meus Agendamentos")** *(Mapeamento: RF023, RF024)*
**História:** Como profissional (barbeiro), quero acessar a aba "Meus Agendamentos" para visualizar os atendimentos do meu dia e mudar o status deles.
**Critérios de Aceite:**
- **RF023:** Aba que mostra a grade de atendimentos do profissional logado, listando dia, horário, cliente e serviços que serão feitos.
- **RF024:** Botões de ação rápida para que o profissional possa alterar o status para "Concluído" ou "Cliente Faltou".

**US-MVP.10: Área do Cliente e Cancelamento** *(Mapeamento: RF025)*
**História:** Como cliente final, quero acessar o meu próprio painel para ver meus agendamentos e poder cancelar um caso ocorra um imprevisto.
**Critérios de Aceite:**
- **RF025:** Área restrita do cliente exibindo a lista de agendamentos. A possibilidade de cancelamento deve estar disponível para agendamentos futuros. O prazo mínimo (ex: 1 hora) pode ser fixo (hardcoded) no motor backend do MVP.
