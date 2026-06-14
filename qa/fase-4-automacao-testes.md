# FASE 4 - Automacao de Testes

## 1. Ferramentas adotadas
1. JUnit 5 + Mockito para testes unitarios.
2. Spring MockMvc para testes de integracao web.
3. DataJpaTest + H2 para persistencia.
4. Playwright para E2E do fluxo critico de cadastro.

## 2. Cobertura automatizada criada no projeto
- Unitario: ReservaServiceTest
- Integracao web: ReservaControllerWebTest
- Repositorio: ReservaRepositoryTest
- E2E: reserva-fluxo-critico.spec.js

## 3. Comandos de execucao
### Maven (unitario + integracao + repositorio)
- mvn clean test

### E2E (Playwright)
1. Subir aplicacao (ex.: docker compose up --build).
2. Em outra aba: cd e2e/playwright
3. npm install
4. npx playwright install
5. npm run test:e2e

## 4. Observacoes didaticas
1. Os testes nao alteram regra de negocio: so validam comportamento atual.
2. O E2E cobre o caminho de maior valor (cadastro e visualizacao na lista).
3. O proximo passo natural e adicionar cenarios de erro e regressao negativa.
