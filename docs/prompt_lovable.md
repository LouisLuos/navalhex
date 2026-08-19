# Prompt Estruturado para o Lovable.dev — Plataforma Navalhex (MVP)

Copie e cole o prompt abaixo no [Lovable.dev](https://lovable.dev) para gerar a aplicação completa do MVP.

---

```markdown
Crie uma aplicação web moderna, responsiva e completa para o projeto **Navalhex**, uma plataforma multi-tenant de agendamento online para barbearias focada em usabilidade e aprendizado prático de arquitetura.

### 🎨 Identidade Visual e Estilo (Design System)
- **Tema:** Dark mode elegante e sofisticado (estilo barbearia premium / classic barbershop).
- **Paleta:** Fundo em tons grafite escuro (`#121214` e `#1e1e24`), detalhes/acentos em âmbar/dourado suave (`#d97706` ou `#f59e0b`), tipografia moderna e limpa, cards com bordas sutis e efeito glassmorphism.
- **Componentes:** Use Lucide Icons, Shadcn UI / Radix primitives, inputs com feedback visual claro e badges coloridos para status (Pendente, Confirmado, Concluído, Cancelado).
- **Auxiliar Didático de Aprendizado:** No topo ou canto da tela, inclua uma barra/menu de alternância rápida de perfil (Role Switcher: "Como Cliente", "Como Barbeiro", "Como Dono/Admin"), permitindo navegar e testar todos os fluxos com dados mockados em memória.

---

### 📱 Estrutura de Telas e Fluxos a Implementar:

#### 1. Autenticação & Switcher de Perfis
- **Tela de Login e Cadastro:** Formulário limpo com campos (Nome, E-mail, WhatsApp, Senha) e seleção de perfil (Cliente, Barbeiro, Administrador) para facilitar os testes.
- Estado de autenticação mockado com persistência local (LocalStorage/React State).

#### 2. Fluxo Público de Agendamento do Cliente (`/{slug}` ou `/barbearia-vintage`)
- **Página Inicial da Barbearia:** Header com logotipo, nome fantasia, endereço e contato.
- **Catálogo de Serviços (Multi-seleção):** Cards de serviços (Corte, Barba, Combo, etc.) com Nome, Preço (R$) e Duração (min). O cliente pode selecionar múltiplos serviços, e um rodapé/drawer flutuante exibe o resumo em tempo real da duração total e valor somado.
- **Seleção de Barbeiro:** Listagem dos profissionais que executam os serviços selecionados (com foto mockada e nome).
- **Seletor de Data e Horários (Slots):** Calendário com dias e lista de horários disponíveis calculados com base na duração total somada.
- **Resumo e Confirmação (Checkout Visual):** Modal/tela de revisão dos dados do agendamento, dados de contato e botão "Confirmar Reserva" que gera a reserva e exibe tela de sucesso.

#### 3. Painel do Administrador da Barbearia (Tenant Admin)
- **Configurações da Barbearia:** Formulário para editar Nome Fantasia, Slug da URL, Endereço e Link do WhatsApp.
- **Gestão de Serviços:** Tabela/Cards com CRUD completo (Adicionar, Editar, Ativar/Desativar) contendo Nome, Descrição, Preço e Duração em minutos.
- **Gestão de Equipe (Barbeiros):** Cadastro de profissionais e checklist para vincular quais serviços cada barbeiro está habilitado a prestar.
- **Jornada de Trabalho:** Configuração da grade horária semanal por profissional (dias da semana, horário de início, fim e intervalo de almoço).

#### 4. Painel do Barbeiro ("Meus Agendamentos")
- **Agenda do Dia/Semana:** Lista de atendimentos agendados exibindo: horário de início/fim, nome e WhatsApp do cliente, serviços a serem realizados e valor.
- **Ações Rápidas:** Botões para mudar status com um clique: `Concluído` (verde), `Cliente Faltou` (vermelho) ou `Em Atendimento`.

#### 5. Painel do Cliente Final ("Minhas Reservas")
- **Lista de Agendamentos:** Cards divididos entre "Próximos Agendamentos" e "Histórico".
- **Cancelamento:** Botão "Cancelar Agendamento" com confirmação (simulando a regra de cancelamento com antecedência mínima).

---

### ⚙️ Dados Mockados Iniciais (Mock Data)
- Crie um arquivo/estado central com dados realistas pré-carregados:
  - 1 Barbearia de exemplo: "Navalha Vintage Club" (`slug: navalha-vintage`).
  - 3 Barbeiros: "Lucas 'Navalha'", "Carlos Silva", "Mateus Barber".
  - 5 Serviços: Corte Degradê (R$ 45, 30min), Barba Completa (R$ 35, 30min), Combo Cabelo + Barba (R$ 70, 50min), Hidratação (R$ 30, 20min), Sobrancelha (R$ 15, 15min).
  - 3 Agendamentos de exemplo distribuídos nos status (Agendado, Concluído, Cancelado).

Garanta que todas as telas sejam interativas, que seja possível navegar fluidamente entre os módulos e que a interface seja visualmente impressionante e intuitiva para celular e desktop.
```
