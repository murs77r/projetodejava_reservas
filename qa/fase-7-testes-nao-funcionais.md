# FASE 7 - Testes Nao Funcionais

## 1. Escolha do teste nao funcional
Teste de performance/carga inicial com Apache JMeter.

## 2. Objetivo
Validar comportamento do endpoint principal sob carga moderada, medindo tempo de resposta e taxa de erro.

## 3. Cenario inicial proposto
- Endpoint alvo: GET /reservas
- Usuarios virtuais: 30
- Ramp-up: 30 segundos
- Duracao: 3 minutos
- Meta de latencia p95: <= 800 ms
- Meta de erro: < 1%

## 4. Passos de execucao
1. Subir aplicacao com Docker.
2. Criar plano no JMeter com Thread Group + HTTP Request + Listeners.
3. Executar teste e exportar relatorio HTML.
4. Registrar resultados no dashboard de qualidade.

## 5. Criterios de aprovacao
1. p95 dentro da meta.
2. Taxa de erro abaixo da meta.
3. Sem degradacao severa de resposta ao longo do teste.

## 6. Evolucao (seguranca)
Como segundo ciclo, executar baseline de seguranca com OWASP ZAP (scan passivo + ativo leve).
