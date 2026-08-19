# Product Backlog — Refinamento INVEST Completo

Muitas das histórias originais apresentavam características de **Épicos**, englobando múltiplos fluxos de valor, integrações complexas e diferentes interfaces de usuário. 

Aplicando os princípios **INVEST** (Independent, Negotiable, Valuable, Estimable, Small, Testable), os épicos foram destrinchados em histórias de usuário menores, focadas em entregas específicas de valor. Cada história agora está devidamente rastreada com o respectivo Requisito Funcional (RF).

---

## Módulo 1: Autenticação, Usuários e Perfis (Auth & RBAC)

**US01.1: Cadastro de Cliente via E-mail** *(Ref: RF001)*
**História:** Como cliente final, quero me cadastrar usando meu e-mail, nome e WhatsApp, para criar minha conta na plataforma.
**Critérios de Aceite:**
- Validação de formato de e-mail e complexidade de senha.
- Obrigatoriedade dos campos: nome, e-mail, telefone (WhatsApp) e senha.
- Criação do usuário com a role `ROLE_CUSTOMER`.

**US01.2: Login via E-mail e Senha** *(Ref: RF002)*
**História:** Como usuário do sistema, quero realizar login com meu e-mail e senha, para acessar minhas funcionalidades restritas com segurança.
**Critérios de Aceite:**
- Validação das credenciais no backend (hash de senha).
- Geração e retorno de token JWT válido contendo as claims de perfil (`ROLE_CUSTOMER`, `ROLE_TENANT_ADMIN`, `ROLE_PROFESSIONAL`, `ROLE_SUPERADMIN`).
- Tratamento de erro amigável para credenciais inválidas.

**US01.3: Login Social (Google OAuth2)** *(Ref: RF003)*
**História:** Como cliente final, quero fazer login usando minha conta do Google, para acessar a plataforma rapidamente sem criar uma nova senha.
**Critérios de Aceite:**
- Redirecionamento para a tela de consentimento segura do Google (OAuth2).
- Provisionamento automático da conta no banco local caso o e-mail não exista.
- Emissão do token JWT padrão do sistema após o handshake.

**US01.4: Recuperação de Senha** *(Ref: RF004)*
**História:** Como usuário do sistema, quero solicitar a redefinição da minha senha via link, para recuperar o acesso caso eu a esqueça.
**Critérios de Aceite:**
- Envio de token seguro via e-mail ou WhatsApp com tempo de expiração.
- Tela para inserção da nova senha e confirmação.
- Invalidação imediata do token após o uso bem-sucedido.

---

## Módulo 2: Gestão de Assinatura SaaS e Onboarding

**US02.1: Onboarding Inicial do Estabelecimento** *(Ref: RF005)*
**História:** Como dono de barbearia, quero cadastrar meu estabelecimento informando Nome Fantasia e Slug da URL, para iniciar minha presença na plataforma.
**Critérios de Aceite:**
- Formulário solicitando Nome Fantasia, Slug único (ex: `/navalha-club`) e seleção do plano de assinatura.
- Validação de unicidade do slug.
- Atribuição automática da role `ROLE_TENANT_ADMIN` ao usuário criador.

**US02.2: Checkout de Assinatura via Mercado Pago** *(Ref: RF006)*
**História:** Como Tenant Admin, quero selecionar um plano e cadastrar meu método de pagamento (PIX ou Cartão), para gerenciar a cobrança recorrente da minha assinatura.
**Critérios de Aceite:**
- Integração com a API do Mercado Pago para criação de assinatura (cobrança recorrente via cartão de crédito ou PIX).
- Registro da assinatura no banco com status inicial apropriado.

**US02.3: Sincronização de Status da Assinatura (Webhooks)** *(Ref: RF007)*
**História:** Como sistema, quero escutar eventos do Mercado Pago via webhook, para atualizar dinamicamente o status da assinatura do tenant.
**Critérios de Aceite:**
- Endpoint seguro para escutar eventos de pagamento.
- Atualização do status da assinatura no banco de dados local (`ACTIVE`, `PAST_DUE`, `CANCELED`, `TRIAL`).

**US02.4: Bloqueio Automático por Inadimplência** *(Ref: RF008)*
**História:** Como sistema, quero bloquear o acesso público e restringir o painel, caso a assinatura do tenant esteja em atraso ou cancelada.
**Critérios de Aceite:**
- Se o status do Tenant for `PAST_DUE` ou `CANCELED`, bloquear automaticamente o acesso ao link público de agendamentos.
- Painel do Tenant Admin deve limitar a visão e bloquear edições, exigindo regularização.

---

## Módulo 3: Configuração da Barbearia e Serviços

**US03.1: Configuração do Perfil do Estabelecimento** *(Ref: RF009)*
**História:** Como Tenant Admin, quero configurar os dados de contato e visuais da barbearia, para que a página reflita a identidade da minha marca.
**Critérios de Aceite:**
- Formulário para edição de: Logotipo/Banner, Endereço completo, Telefone de contato, Links de redes sociais e Mensagem de boas-vindas.

**US03.2: Cadastro e Gestão de Serviços** *(Ref: RF010)*
**História:** Como Tenant Admin, quero criar, editar e desativar os serviços oferecidos, definindo preço e duração.
**Critérios de Aceite:**
- Inclusão de campos: Nome, Descrição opcional, Preço (R$) e Duração estimada (em minutos).
- Suporte a desativação para não excluir registros antigos em relatórios.

**US03.3: Categorização de Serviços** *(Ref: RF011)*
**História:** Como Tenant Admin, quero criar categorias para agrupar os serviços oferecidos de forma organizada na página pública.
**Critérios de Aceite:**
- Permitir agrupar serviços por categorias (ex: Cabelo, Barba, Combos, Tratamentos Especializados).

---

## Módulo 4: Gestão de Equipe e Grade Horária

**US04.1: Cadastro de Barbeiros/Profissionais** *(Ref: RF012)*
**História:** Como Tenant Admin, quero cadastrar membros da equipe informando seus dados e vinculando quais serviços realizam.
**Critérios de Aceite:**
- Formulário com nome, e-mail, telefone (WhatsApp) e foto (opcional).
- Seleção dos serviços que aquele profissional está habilitado a executar.

**US04.2: Definição da Jornada de Trabalho** *(Ref: RF013)*
**História:** Como sistema, preciso permitir a parametrização da grade semanal individual de cada barbeiro, para estabelecer a base da disponibilidade.
**Critérios de Aceite:**
- Definição dos dias de trabalho da semana.
- Definição do horário de início e fim da jornada.
- Definição do intervalo para almoço/descanso.

**US04.3: Gestão de Bloqueios de Agenda** *(Ref: RF014)*
**História:** Como profissional ou administrador, quero criar bloqueios pontuais na agenda, para registrar folgas, feriados ou ausências médicas.
**Critérios de Aceite:**
- Inserção de bloqueio contendo data/hora de início e fim e motivo.
- O motor de busca de horários deve passar a ignorar esses intervalos de tempo.

---

## Módulo 5: Fluxo de Agendamento Público

**US05.1: Acesso via Link Personalizado** *(Ref: RF015)*
**História:** Como cliente final, quero visualizar a página pública da barbearia através da rota customizada, sem necessidade de login prévio.
**Critérios de Aceite:**
- Acesso aberto via rota `/{slug-da-barbearia}`.
- Exibição de todo o catálogo de serviços ativos navegável na página inicial.

**US05.2: Seleção de Multi-Serviços** *(Ref: RF016)*
**História:** Como cliente final, quero poder escolher um ou mais serviços na mesma sessão de agendamento.
**Critérios de Aceite:**
- O sistema deve somar as durações e os valores (R$) dinamicamente no frontend.

**US05.3: Escolha de Barbeiro** *(Ref: RF017)*
**História:** Como cliente final, quero selecionar um barbeiro específico ou escolher "Qualquer profissional disponível".
**Critérios de Aceite:**
- O sistema permite escolher um barbeiro ou delegar ao motor de busca.

**US06.1: Cálculo Dinâmico de Slots Disponíveis** *(Ref: RF018)*
**História:** Como cliente final, quero ver apenas os horários livres reais, cruzados com todos os bloqueios e jornadas, para evitar conflitos de agenda.
**Critérios de Aceite:**
- O sistema deve cruzar a duração total dos serviços selecionados com a jornada de trabalho do barbeiro.
- O algoritmo subtrai agendamentos já existentes e bloqueios pontuais.
- Exibe estritamente horários com slots contínuos suficientes para a realização integral do procedimento.

**US06.2: Confirmação e Reserva** *(Ref: RF019)*
**História:** Como cliente final, quero me autenticar e confirmar o agendamento em uma tela de resumo com os dados finais.
**Critérios de Aceite:**
- Tela de resumo com valor final, data e horário.
- Se o usuário não estiver logado, forçar a autenticação/cadastro sem perder os itens selecionados da sessão.

---

## Módulo 6: Notificações Automáticas

**US07.1: Notificação de Confirmação** *(Ref: RF020)*
**História:** Como sistema, após concluir o agendamento, devo disparar uma mensagem automática via WhatsApp para o cliente com o resumo do pedido.
**Critérios de Aceite:**
- Integração via WhatsApp API.
- A mensagem contém: nome do profissional, serviço(s) selecionado(s), data, horário e valor total (R$).

**US07.2: Lembrete Pré-Agendamento** *(Ref: RF021)*
**História:** Como sistema, devo enviar um lembrete automático via WhatsApp X horas antes do atendimento agendado.
**Critérios de Aceite:**
- Cron job que dispara a notificação antes do atendimento (ex: 2 horas antes).

**US07.3: Notificação de Cancelamento/Reagendamento** *(Ref: RF022)*
**História:** Como sistema, devo notificar tanto o cliente quanto o profissional caso um agendamento seja cancelado ou alterado.
**Critérios de Aceite:**
- Disparo de mensagem informativa sempre que o status do agendamento mudar para cancelado.

---

## Módulo 7: Gestão e Operação de Agendamentos

**US08.1: Painel do Barbeiro ("Meus Agendamentos")** *(Ref: RF023)*
**História:** Como profissional, quero acessar uma aba "Meus Agendamentos" para visualizar minha grade do dia e detalhes do cliente.
**Critérios de Aceite:**
- Visão restrita aos agendamentos do profissional.
- Exibição expressa de: dia, horário, cliente e serviços.
- Filtros por status: Agendado, Concluído, Cancelado, Não Compareceu.

**US08.2: Alteração de Status do Agendamento** *(Ref: RF024)*
**História:** Como profissional, quero alterar o status do atendimento para "Concluído" ou "Cliente Faltou".
**Critérios de Aceite:**
- Ações rápidas no card para transição do status de atendimento.

**US09.1: Política de Cancelamento do Cliente** *(Ref: RF025)*
**História:** Como cliente final, quero poder cancelar meus agendamentos diretamente no painel respeitando o prazo mínimo definido.
**Critérios de Aceite:**
- O cliente visualiza a opção de cancelar no painel.
- O botão respeita o prazo mínimo estabelecido pela barbearia (ex: não permitir cancelar restando menos de 1 hora).

---

## Módulo 8: Painel Administrativo Global (Super Admin)

**US10.1: Gestão de Tenants** *(Ref: RF026)*
**História:** Como Super Admin da plataforma, quero listar as barbearias, filtrar por status de assinatura e ativá-las/desativá-las manualmente.
**Critérios de Aceite:**
- Listagem global de tenants cadastrados.
- Ação manual para bypass e ativação/desativação do estabelecimento.

**US10.2: Dashboard de Métricas SaaS** *(Ref: RF027)*
**História:** Como Super Admin, quero visualizar métricas globais para monitorar a saúde do meu negócio.
**Critérios de Aceite:**
- Exibição de MRR (Receita Recorrente Mensal).
- Quantidade total de tenants ativos na plataforma.
- Volume geral de agendamentos realizados no mês.
