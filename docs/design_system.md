# Design System — Navalhex

Este documento estabelece o guia oficial de design, tokens visuais, tipografia e componentes do **Navalhex**. 

Adotamos um padrão **clean, sóbrio e moderno** (inspirado no padrão de produtos como Linear, Stripe e Shadcn UI): sem firulas, sem sombras ou brilhos excessivos, com alto contraste, excelente legibilidade e suporte limpo a Light & Dark Theme.

---

## 1. Princípios de Design

1. **Direto ao Ponto:** Foco na clareza dos dados e facilidade de agendamento.
2. **Neutro & Sofisticado:** Paleta baseada em tons neutros (Zinc/Slate) com elementos pretos, brancos e sutis toques de azul para ações principais.
3. **Bordas Nítidas:** Divisores e bordas finas com contraste suave em vez de sombras difusas pesadas.
4. **Consistência:** Mesma linguagem visual no fluxo público do cliente e no painel administrativo do barbeiro.

---

## 2. Paleta de Cores & Tokens

### 2.1 Tema Escuro (Dark Mode — Padrão)

| Token | Tailwind Class | Hex / Valor | Uso |
| :--- | :--- | :--- | :--- |
| `bg-app` | `bg-zinc-950` | `#09090b` | Fundo principal da aplicação |
| `bg-surface` | `bg-zinc-900` | `#18181b` | Cards, containers, modais e headers |
| `bg-surface-subtle` | `bg-zinc-800/60` | `#27272a` | Hover de itens, badges neutras, chips |
| `border-subtle` | `border-zinc-800` | `#27272a` | Bordas e linhas divisórias padrão |
| `border-focus` | `border-zinc-500` | `#71717a` | Estado de foco em inputs |
| `text-primary` | `text-zinc-100` | `#f4f4f5` | Títulos e textos de alto contraste |
| `text-secondary` | `text-zinc-400` | `#a1a1aa` | Parágrafos e descrições |
| `text-muted` | `text-zinc-500` | `#71717a` | Placeholders, legendas secundárias |

### 2.2 Tema Claro (Light Mode)

| Token | Tailwind Class | Hex / Valor | Uso |
| :--- | :--- | :--- | :--- |
| `bg-app` | `bg-zinc-50` | `#fafafa` | Fundo principal da aplicação |
| `bg-surface` | `bg-white` | `#ffffff` | Cards, containers, modais e headers |
| `bg-surface-subtle` | `bg-zinc-100` | `#f4f4f5` | Hover de itens, badges neutras |
| `border-subtle` | `border-zinc-200` | `#e4e4e7` | Bordas e linhas divisórias |
| `border-focus` | `border-zinc-400` | `#a1a1aa` | Estado de foco em inputs |
| `text-primary` | `text-zinc-900` | `#18181b` | Títulos e textos principais |
| `text-secondary` | `text-zinc-600` | `#52525b` | Parágrafos e descrições |
| `text-muted` | `text-zinc-400` | `#a1a1aa` | Placeholders e legendas |

### 2.3 Cores de Ação & Status

*   **Ação Primária (CTA):** 
    *   Dark: `bg-white hover:bg-zinc-200 text-zinc-950 font-medium` (ou `bg-zinc-100 text-zinc-900`).
    *   Light: `bg-zinc-900 hover:bg-zinc-800 text-white font-medium`.
*   **Sucesso / Ativo:** `text-emerald-500` / `bg-emerald-500/10` / `border-emerald-500/20`.
*   **Atenção:** `text-amber-500` / `bg-amber-500/10` / `border-amber-500/20`.
*   **Erro / Cancelado:** `text-red-500` / `bg-red-500/10` / `border-red-500/20`.
*   **Informativo / WhatsApp:** `text-emerald-500` / `bg-emerald-500/10`.

---

## 3. Tipografia & Hierarquia

*   **Fonte:** `Inter`, `system-ui`, `-apple-system`, `sans-serif`.
*   **H1 (Títulos Principais):** `text-2xl sm:text-3xl font-bold tracking-tight`.
*   **H2 (Seções):** `text-lg sm:text-xl font-semibold tracking-tight`.
*   **H3 / Card Titles:** `text-sm sm:text-base font-semibold`.
*   **Corpo:** `text-sm text-zinc-400 leading-relaxed`.
*   **Labels & Badges:** `text-xs font-medium uppercase tracking-wider`.

---

## 4. Componentes

### 4.1 Botões
*   **Primário:** `px-4 py-2 rounded-xl text-xs font-semibold bg-white text-zinc-950 hover:bg-zinc-200 transition active:scale-[0.98]`.
*   **Secundário:** `px-4 py-2 rounded-xl text-xs font-medium bg-zinc-900 hover:bg-zinc-800 text-zinc-300 border border-zinc-800 transition`.
*   **Destrutivo / Perigo:** `px-3 py-1.5 rounded-lg text-xs font-medium text-red-400 hover:bg-red-950/30 transition`.

### 4.2 Inputs
*   `px-3.5 py-2.5 rounded-xl bg-zinc-900 border border-zinc-800 text-zinc-100 placeholder-zinc-500 focus:outline-none focus:border-zinc-500 text-sm`.

### 4.3 Cards
*   `bg-zinc-900 border border-zinc-800 rounded-2xl p-5 shadow-sm`.
