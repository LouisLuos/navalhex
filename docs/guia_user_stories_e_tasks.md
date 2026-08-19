# Guia: Atrelando Tarefas Técnicas a Histórias de Usuário

Atrelar tarefas técnicas a uma História de Usuário (User Story) é uma prática fundamental nas metodologias ágeis (como Scrum ou Kanban) para garantir que o desenvolvimento técnico esteja sempre alinhado com a entrega de valor para o cliente.

Este documento serve como referência para organizar o fluxo de desenvolvimento no projeto Navalhex.

## 1. Entenda a Relação (O "Quê" vs. O "Como")

*   **História de Usuário (User Story):** Representa o **"Quê"** e o **"Por que"**. É descrita na perspectiva do usuário e foca no valor de negócio. 
    *   *Ex: "Como cliente final, quero me cadastrar usando meu e-mail, nome e WhatsApp, para criar minha conta na plataforma."*
*   **Tarefa Técnica (Sub-task):** Representa o **"Como"**. É o trabalho de engenharia necessário para fazer a história de usuário funcionar. É escrita de desenvolvedor para desenvolvedor.

## 2. A Prática de "Quebra" (Breakdown)

Antes de iniciar o desenvolvimento, o desenvolvedor deve pegar uma História de Usuário e "quebrá-la" em tarefas técnicas menores.

**Regras de ouro para quebrar em tarefas:**

*   **Granularidade:** Cada tarefa técnica deve ser pequena o suficiente para ser concluída em poucas horas ou, no máximo, 1 a 2 dias de trabalho focado.
*   **Independência:** Tente criar tarefas que possam ser desenvolvidas de forma semi-independente (ex: Backend primeiro, depois Frontend), se a arquitetura permitir.
*   **Rastreabilidade:** Toda tarefa técnica **deve** estar atrelada a uma História de Usuário. Não devem existir tarefas técnicas complexas "flutuando" sozinhas sem gerar valor para um Requisito Funcional ou História.

## 3. Exemplo Prático de Quebra (Baseado no Backlog)

Pegando a US01.1 do backlog do projeto como exemplo prático:

**História de Usuário (US01.1):**
> *Como cliente final, quero me cadastrar usando meu e-mail, nome e WhatsApp, para criar minha conta na plataforma.*
> **Critérios de Aceite:**
> - Validação de formato de e-mail e complexidade de senha.
> - Obrigatoriedade dos campos: nome, e-mail, telefone (WhatsApp) e senha.
> - Criação do usuário com a role `ROLE_CUSTOMER`.

**Tarefas Técnicas Atreladas (Sub-tasks de US01.1):**
*   `[Task-1]` (Banco de Dados): Modelar e criar a entidade/tabela `users` com os campos `id`, `name`, `email`, `whatsapp`, `password` e `role`.
*   `[Task-2]` (Backend): Implementar validações no DTO de entrada (Regex de email, senha forte, campos obrigatórios).
*   `[Task-3]` (Backend): Implementar o caso de uso e o endpoint `POST /api/users/register` incluindo o hash de senha e atribuição automática da role `ROLE_CUSTOMER`.
*   `[Task-4]` (Frontend): Criar a UI (tela e formulário) de cadastro com os campos requeridos.
*   `[Task-5]` (Frontend): Integrar o formulário com a API do backend, incluindo tratamento de estado e mensagens de erro (ex: e-mail já existente).
*   `[Task-6]` (Testes): Escrever testes (unitários/integração) cobrindo os caminhos felizes e de erro do cadastro.

## 4. Como organizar no Repositório / Ferramentas

*   **Gerenciador de Tarefas (Ex: GitHub Projects):** A História de Usuário é um item (Issue) principal. As tarefas técnicas podem ser criadas como um checklist (`- [ ] Tarefa X`) dentro da descrição da própria Issue principal, ou como sub-issues referenciando a issue pai.
*   **No código (Git):** Use a referência da História de Usuário no nome da branch e as tarefas nas mensagens de commit.
    *   *Nome da Branch:* `git checkout -b feature/US01.1-cadastro-cliente`
    *   *Mensagem de Commit:* `git commit -m "feat(auth): [Task-3] cria endpoint e caso de uso de registro de cliente"`

## 5. Benefícios dessa prática no desenvolvimento diário

1.  **Transparência:** Facilita entender a complexidade técnica invisível (ex: configuração de banco) necessária para entregar uma feature.
2.  **Estimativas mais precisas:** É muito mais fácil estimar o tempo de tarefas pequenas separadamente do que o esforço de uma História inteira de uma vez.
3.  **Progresso visível:** O ato de "ticar" (marcar como concluída) tarefas técnicas menores gera sensação de avanço contínuo e evita a síndrome do "está quase pronto" por dias a fio.
