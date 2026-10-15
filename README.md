# 🏢 Sistema de Telemetria de Umidade Agrícola - Smart Irrigation

## 🎯 O que o projeto faz?

O sistema simula um módulo de telemetria automatizado para monitoramento de solo e suporte a sistemas de irrigação inteligente em canteiros agrícolas. O usuário registra leituras de umidade organizadas em uma matriz (representando múltiplos canteiros e seus respectivos sensores). O programa realiza a validação dos dados em tempo real (bloqueando valores inconsistentes fora da faixa de 0% a 100%). Ao final, o sistema varre a grade de dados para calcular a **média geral de umidade do campo** e contabilizar a **quantidade de pontos críticos com solo seco** (umidade abaixo de 40%) que necessitam de irrigação imediata.

### 🔄 Fluxo da Aplicação
```text
[ Entrada de Umidade ] ➔ [ Validação de Faixa (0% a 100%) ] ➔ [ Mapeamento em Matriz 3x3 ] ➔ [ Análise de Solo Seco ] ➔ [ Relatório Geral & Alertas ]
```

---

## 🎨 Design do Código (Como as regras conversam)

Este projeto foi construído utilizando uma arquitetura baseada em **Matrizes (Arrays Bidimensionais)** de precisão flutuante para mapear a distribuição espacial dos sensores de campo. O controle de fluxo e o processamento analítico ocorrem por meio de **Laços de Repetição Aninhados (Loops For Encadeados)** que separam a fase de varredura topográfica da fase de consolidação estatística. Veja a estrutura limpa do design:

```text
       ┌────────────────────────┐
       │   Leitura dos Sensores │ (Estrutura de loops 'for' aninhados para percorrer linhas e colunas)
       └───────────┬────────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │ Filtro de Faixa Segura │ ◄───┐ (Loop 'while' de barreira contra porcentagens inválidas)
       └───────────┬────────────┘     │
                   │                  │
                   ▼                  │ (Entrada inválida)
       ┌────────────────────────┐     │
       │ Armazenamento na Grade │ ────┘
       └───────────┬────────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │   Módulo Analítico     │ (Varredura for-encadeada para leitura bidimensional)
       └───────────┬────────────┘
                   │
         ┌─────────┴─────────┐
         ▼                   ▼
   [Cálculo de Média]   [Filtro Crítico]
   (Acumula valores)    (Se < 40%, gera Alerta e incrementa pontosSecos)
         │                   │
         └─────────┬─────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │   Relatório Agrícola   │ (Geração do diagnóstico consolidado do solo)
       └────────────────────────┘
```

### 📋 Mapeamento de Variáveis e Estruturas

| Estrutura / Variável | O que ela faz no código? |
| :--- | :--- |
| 🚀 `main.java` | Classe principal executável que gerencia a entrada de telemetria, gerencia a grade e processa os dados de campo. |
| 📊 `tele` | Matriz bidimensional de tipo `double` com dimensões 3x3 (`new double[3][3]`), usada para simular 3 canteiros com 3 sensores cada. |
| 💧 `media` | Variável acumuladora global que soma todas as porcentagens coletadas para determinar o nível de hidratação médio. |
| ⚠️ `pontosSecos` | Contador de incidentes ativado sempre que uma célula da matriz acusa umidade crítica menor que 40%. |
| 📉 `mediaGeral` | Armazena a divisão do acumulador total pelo número fixo de sensores (9) para o parecer de eficiência do ecossistema. |

---

## 🧠 Conceitos de Programação Praticados

* **Estrutura de Arrays Bidimensionais (Matrizes):** Implementação de grades de dados estruturadas em linhas e colunas (`i` e `j`), ideais para simulações topográficas ou coordenadas espaciais.
* **Laços de Repetição Encadeados (Loops Aninhados):** Uso de loops `for` de forma aninhada para navegar por todas as dimensões da matriz, controlando de forma coordenada índices de canteiros e sensores.
* **Mecânica de Acumulação Cumulativa:** Algoritmo iterativo capaz de consolidar estatísticas gerais e gatilhos de alerta ao mesmo tempo em que expõe individualmente as propriedades de cada sensor.

---

## 💻 Exemplo Prático de Uso

Imagine a seguinte simulação de auditoria de solo contendo uma entrada irregular e disparos de alerta críticos rodando direto no terminal:

```text
Digite a umidade do Canteiro 1, Sensor 1:
45.5
Digite a umidade do Canteiro 1, Sensor 2:
120.0
Valor inválido, digite novamente os valor da umidade: 
35.0
... (demais sensores inseridos) ...

=============================================
Canteiro 1 - Sensor 1, valor da umidade: 45.50
Canteiro 1 - Sensor 2, valor da umidade: 35.00
⚠️ [ALERTA] Solo seco detectado no Canteiro 1, Sensor 2
===========================================
======== Relatório Geral ===============
Média geral de umidade 62.45
Quantidade de pontos críticos que precisam de irrigação : 1
```
