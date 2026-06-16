# Sistema de Reservas

Projeto simples em Spring Boot + Thymeleaf + MySQL.

Grupo formado por Murilo Souza, João Paulo Nunes e Victor de Castro.

## Executar localmente (com Docker)

```bash
docker compose up --build
```

Acesse: http://localhost:8080

## Deploy externo / Railway

O container da aplicação sobe com o profile `docker`.
Para funcionar fora do `docker compose`, defina estas variáveis de ambiente no serviço da aplicação:

```text
SPRING_DATASOURCE_URL=jdbc:mysql://SEU_HOST:3306/SEU_BANCO?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=SEU_USUARIO
SPRING_DATASOURCE_PASSWORD=SUA_SENHA
PORT=8080
```

Sem essas variáveis, o profile `docker` usa por padrão o host `db`, que só existe dentro da rede do Docker Compose.

## Qualidade e Testes (QA)

### Testes automatizados Java (unitario + integracao)

```bash
mvn clean test
```

### Relatório de cobertura JaCoCo

Gerado em:

```text
target/site/jacoco/index.html
```

### Testes E2E com Playwright

Com a aplicação rodando em `http://localhost:8080`:

```bash
cd e2e/playwright
npm install
npx playwright install
npm run test:e2e
```

### CI/CD de qualidade

O pipeline de CI está em:

```text
.github/workflows/ci-qa.yml
```

Ele executa build, testes e publica artefatos de relatórios.

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
