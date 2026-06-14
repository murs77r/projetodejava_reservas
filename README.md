# Sistema de Reservas

Projeto simples em Spring Boot + Thymeleaf + MySQL.

## Executar localmente (com Docker)

```bash
docker compose up --build
```

Acesse: http://localhost:8080

## Qualidade e Testes (QA)

### Testes automatizados Java (unitario + integracao)

```bash
mvn clean test
```

### Relatorio de cobertura JaCoCo

Gerado em:

```text
target/site/jacoco/index.html
```

### Testes E2E com Playwright

Com a aplicacao rodando em `http://localhost:8080`:

```bash
cd e2e/playwright
npm install
npx playwright install
npm run test:e2e
```

### CI/CD de qualidade

O pipeline de CI esta em:

```text
.github/workflows/ci-qa.yml
```

Ele executa build, testes e publica artefatos de relatorios.

## Parar os containers

```bash
docker compose down
```

## Executar localmente (sem Docker)

Pré-requisito: MySQL rodando com banco `reservas_db` e usuário/senha `root`.

```bash
mvn spring-boot:run
```

Acesse: http://localhost:8080
