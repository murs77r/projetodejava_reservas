# FASE 6 - Pipeline de Qualidade (CI/CD)

## 1. Pipeline implementado
Arquivo: .github/workflows/ci-qa.yml

## 2. O que o pipeline faz
1. Checkout do codigo.
2. Setup Java 17 com cache Maven.
3. Build e testes Java (unitario, integracao e repositorio) com Maven.
4. Publicacao dos relatorios de teste e cobertura.
5. Setup Node e Playwright para testes E2E.
6. Subida da aplicacao no perfil CI e espera de disponibilidade.
7. Execucao do fluxo E2E critico automatizado.
8. Publicacao do relatorio Playwright e log da aplicacao.

## 3. Beneficios para a startup
1. Detecta regressao no pull request.
2. Gera evidencias de qualidade automaticamente.
3. Reduz retrabalho e bugs em producao.

## 4. Evolucao recomendada
1. Integrar SonarQube/SonarCloud.
2. Definir bloqueio por cobertura minima.
3. Adicionar stage de deploy para ambiente de homologacao.
