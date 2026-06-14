# FASE 8 - Metricas e Governanca

## 1. Dashboard executivo (estrutura)

### Bloco A - Qualidade de Codigo
1. Cobertura de testes (%): JaCoCo
2. Falhas por pipeline: CI GitHub Actions

### Bloco B - Qualidade de Produto
1. Densidade de defeitos: bugs confirmados / tamanho funcional entregue
2. Taxa de falhas em producao: incidentes / periodo
3. MTTR: tempo medio entre abertura e resolucao de incidente

### Bloco C - Efetividade de Testes
1. Taxa de regressao capturada antes de producao
2. Taxa de sucesso dos testes E2E

## 2. Definicoes rapidas
- Cobertura (%): linhas cobertas / linhas totais x 100
- Taxa de falhas: numero de falhas em producao por semana
- MTTR: media do tempo (em horas) para restaurar o servico

## 3. Rotina de governanca
1. Daily QA (15 min): falhas novas e riscos.
2. Review semanal (30 min): tendencias e acoes corretivas.
3. Gate de release: sem bug critico aberto + pipeline verde.

## 4. Politica minima de qualidade
1. Build quebrado bloqueia merge.
2. Bug critico bloqueia release.
3. Regressao dos fluxos criticos obrigatoria em cada release.
