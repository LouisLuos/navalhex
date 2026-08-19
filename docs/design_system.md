# Design System — Navalhex

Este documento estabelece a especificação completa de design, tokens visuais, tipografia e guia de componentes para o **Navalhex**. Ele serve como guia para implementação no frontend (React, Tailwind CSS, Shadcn UI / Radix primitives).

---

## 1. Visão Geral e Atmosfera (Brand Identity)

O **Navalhex** adota a identidade **"Modern Classic Barbershop"**: uma estética escura (*Dark Mode First*), sofisticada e limpa, unindo a tradição das barbearias clássicas à fluidez de um software moderno de alta performance.

*   **Sensação:** Premium, confiável, masculina e minimalista.
*   **Estilo:** Superfícies escuras (grafite/zinco), toques elegantes em âmbar/dourado suave, bordas translúcidas sutis e tipografia nítida.

---

## 2. Paleta de Cores e Tokens (Color Tokens)

### 2.1 Cores Estruturais e Superfícies (Dark Theme)

| Token | Hex | Tailwind Class | Uso Principal |
| :--- | :--- | :--- | :--- |
| `bg-app` | `#09090B` | `bg-zinc-950` | Fundo principal da aplicação (Background raiz) |
| `bg-surface` | `#18181B` | `bg-zinc-900` | Cards, modais, sidebars e containers |
| `bg-surface-elevated` | `#27272A` | `bg-zinc-800` | Dropdowns, hover de cards, inputs ativos |
| `border-subtle` | `#27272A` / `rgba(255,255,255,0.08)` | `border-zinc-800` | Divisores, bordas de cards e inputs |
| `border-highlight` | `#D97706` | `border-amber-600` | Indicador de item ativo ou selecionado |

### 2.2 Cores de Destaque / Marca (Brand Accent)

| Token | Hex | Tailwind Class | Uso Principal |
| :--- | :--- | :--- | :--- |
| `primary-500` | `#F59E0B` | `bg-amber-500` / `text-amber-500` | Botões primários, ícones de destaque, links |
| `primary-600` | `#D97706` | `bg-amber-600` | Hover de botões primários, bordas ativas |
| `primary-glow` | `rgba(245, 158, 11, 0.15)` | `bg-amber-500/15` | Fundo de badges de destaque e glows de seleção |

### 2.3 Tipografia e Escala de Cinzas (Text Neutrals)

| Token | Hex | Tailwind Class | Uso Principal |
| :--- | :--- | :--- | :--- |
| `text-primary` | `#FAFAFA` | `text-zinc-50` | Títulos (H1, H2, H3) e textos em alto contraste |
| `text-secondary` | `#A1A1AA` | `text-zinc-400` | Corpo de texto, labels e descrições secundárias |
| `text-muted` | `#71717A` | `text-zinc-500` | Placeholders, legendas e textos desabilitados |

### 2.4 Status e Semântica (Feedback Tags)

| Status | Cor de Fundo | Cor do Texto | Borda | Uso |
| :--- | :--- | :--- | :--- | :--- |
| **Concluído / Sucesso** | `bg-emerald-500/10` | `text-emerald-400` | `border-emerald-500/20` | Agendamento finalizado |
| **Agendado / Confirmado** | `bg-sky-500/10` | `text-sky-400` | `border-sky-500/20` | Agendamento futuro confirmado |
| **Pendente / Em Espera** | `bg-amber-500/10` | `text-amber-400` | `border-amber-500/20` | Aguardando confirmação |
| **Cancelado / Faltou** | `bg-rose-500/10` | `text-rose-400` | `border-rose-500/20` | Cancelamento ou no-show |

---

## 3. Tipografia (Typography System)

A tipografia utiliza fontes modernas, legíveis e com boa renderização em qualquer dispositivo.

*   **Família Principal (Interface & Corpo):** `Inter`, `Plus Jakarta Sans` ou `-apple-system, sans-serif`
*   **Família Opcional (Títulos de Destaque / Logotipo):** `Outfit` ou `Cinzel`

### Escala Tipográfica

| Nível | Tamanho | Peso | Line Height | Aplicação |
| :--- | :--- | :--- | :--- | :--- |
| **Display / H1** | `2rem` (32px) | Bold (700) | `1.2` | Título da Barbearia, Cabeçalhos principais |
| **H2** | `1.5rem` (24px) | SemiBold (600) | `1.3` | Seções do Catálogo, Títulos de painéis |
| **H3** | `1.25rem` (20px) | Medium (500) | `1.4` | Títulos de Cards, Nomes de Barbeiros |
| **Body (Base)** | `1rem` (16px) | Regular (400) | `1.5` | Descrições de serviços, textos gerais |
| **Small / Label**| `0.875rem` (14px)| Medium (500) | `1.4` | Labels de formulários, horários, preços |
| **Micro / Caption**| `0.75rem` (12px)| Regular (400) | `1.4` | Badges, datas secundárias, status |

---

## 4. Layout, Espaçamento e Bordas

*   **Border Radius:**
    *   `rounded-md` (6px): Inputs, tags e badges.
    *   `rounded-xl` (12px): Botões principais, slots de horário.
    *   `rounded-2xl` (16px): Cards de serviços, painéis e modais.
    *   `rounded-full`: Avatares e botões circulares de ação.
*   **Espaçamento Base:** Sistema múltiplo de 4px (`gap-2` = 8px, `gap-4` = 16px, `gap-6` = 24px, `p-6` = 24px).
*   **Efeito Glassmorphism & Sombras:**
    *   Cards com leve translucidez: `bg-zinc-900/90 backdrop-blur-md border border-white/5`.
    *   Sombras suaves para elevação: `shadow-lg shadow-black/40`.

---

## 5. Especificação de Componentes (Component Guidelines)

### 5.1 Botões (Buttons)

1.  **Primário (CTA):**
    *   Fundo: `bg-amber-500 hover:bg-amber-600 active:scale-[0.98]`
    *   Texto: `text-zinc-950 font-semibold`
    *   Uso: "Confirmar Agendamento", "Avançar", "Salvar".
2.  **Secundário / Outline:**
    *   Fundo: `bg-transparent hover:bg-zinc-800`
    *   Borda: `border border-zinc-700 text-zinc-200`
    *   Uso: "Voltar", "Filtrar", "Cancelar Ação".
3.  **Perigo (Destructive):**
    *   Fundo: `bg-rose-500/10 hover:bg-rose-500/20`
    *   Texto e Borda: `text-rose-400 border border-rose-500/30`
    *   Uso: "Cancelar Agendamento", "Excluir Serviço".

### 5.2 Card de Serviço (Service Card — Multi-seleção)

*   **Estado Padrão:** Fundo `bg-zinc-900`, borda sutil `border-zinc-800/80`, cursor pointer.
*   **Estado Selecionado:** Borda dourada `border-amber-500`, fundo sutilmente iluminado `bg-amber-500/5`, checkmark preenchido em âmbar.
*   **Estrutura Interna:**
    *   Esquerda: Título do serviço (`font-semibold text-zinc-100`), descrição curta (`text-zinc-400 text-sm`), badge de duração (`⏱ 30 min`).
    *   Direita: Preço destacado (`text-amber-400 font-bold text-lg`) e checkbox customizado.

### 5.3 Seletor de Horários (Time Slot Chips)

*   Grade em grid flexível (`grid-cols-3 sm:grid-cols-4 md:grid-cols-6 gap-2`).
*   **Disponível:** Fundo `bg-zinc-900 border-zinc-800 text-zinc-200 hover:border-amber-500/50`.
*   **Selecionado:** `bg-amber-500 text-zinc-950 font-bold border-amber-500 shadow-md shadow-amber-500/20`.
*   **Ocupado / Indisponível:** `bg-zinc-950 text-zinc-600 border-zinc-900 cursor-not-allowed opacity-40`.

### 5.4 Barra Fixa de Resumo (Floating Bottom Bar - Mobile)

*   Fixada no rodapé em dispositivos móveis (`fixed bottom-0 left-0 right-0 z-50 p-4 bg-zinc-900/95 backdrop-blur border-t border-zinc-800`).
*   Exibe:
    *   Total de itens selecionados e tempo somado: `2 serviços (50 min)`.
    *   Valor total: `R$ 80,00`.
    *   Botão CTA de avançar: `Continuar ->`.

### 5.5 Card de Agendamento Operacional (Barbeiro / Painel)

*   Cabeçalho com horário em destaque (`09:00 - 09:45`) e Badge de Status.
*   Informações do cliente: Nome, Link direto para WhatsApp (`WhatsApp Icon`).
*   Lista de serviços selecionados e valor total.
*   Ações Rápidas: Botões pequenos estilo pílula (`Concluir`, `Faltou`).

---

## 6. Feedback e Micro-interações

*   **Transições:** Utilizar durações rápidas e suaves (`transition-all duration-200 ease-in-out`).
*   **Hover em Cards:** Leve elevação ou clareamento de borda (`hover:border-zinc-700 hover:translate-y-[-1px]`).
*   **Loading State:** Efeito *Skeleton Pulse* cinza escuro (`bg-zinc-800 animate-pulse rounded-md`) para carregamento de slots e serviços.
*   **Empty States:** Ilustração ou ícone minimalista (ex: tesoura/calendário do Lucide) com mensagem clara e amigável: *"Nenhum agendamento encontrado para hoje"*.
