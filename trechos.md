# Trechos para a apresentação

Este arquivo reúne, em um só lugar, os trechos que podem ser mostrados durante a apresentação do Sistema de Reservas, seguindo a ordem do roteiro.

---

## Introdução

### Resumo do projeto

- Projeto: Sistema de Reservas
- Tecnologias principais: Java, Spring Boot, Thymeleaf, MySQL, Docker, Maven e GitHub Actions
- Grupo: Murilo Souza, João Paulo Nunes e Victor de Castro
- Objetivo do sistema: permitir cadastrar, listar, editar e excluir reservas
- Objetivo da parte de qualidade: organizar testes, automação, pipeline e métricas para reduzir regressões e aumentar a confiabilidade

### Comandos principais do projeto

```bash
docker compose up --build
```

```bash
mvn clean test
```

```bash
cd e2e/playwright
npm install
npx playwright install
npm run test:e2e
```

---

## Tópico 1: Diagnóstico de Qualidade

### Fluxos principais identificados

1. Listar reservas
2. Cadastrar reserva
3. Editar reserva
4. Excluir reserva

### Riscos de qualidade mapeados

| Risco | Impacto | Probabilidade | Nível | Mitigação proposta |
|---|---|---|---|---|
| Ausência de testes automatizados | Alto | Alto | Crítico | Criar pirâmide de testes |
| Validação fraca no backend | Alto | Médio/Alto | Alto | Adicionar testes de cenários inválidos e evoluir validações |
| Exclusão por GET | Médio/Alto | Médio | Alto | Cobrir por teste e planejar migração futura para método mais adequado |
| Credenciais fixas em ambiente simples | Alto | Alto | Crítico | Usar variáveis de ambiente e separar configurações por ambiente |
| Falta de pipeline de qualidade | Alto | Alto | Crítico | Criar validação automática com build, testes e artefatos |
| Falta de monitoramento de qualidade | Médio | Alto | Alto | Acompanhar cobertura, defeitos, falhas e tempo médio de correção |

### Controller com os endpoints principais

```java
@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/reservas";
    }

    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "lista-reservas";
    }

    @GetMapping("/reservas/nova")
    public String novaReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "formulario-reserva";
    }

    @PostMapping("/reservas/salvar")
    public String salvarReserva(@ModelAttribute Reserva reserva) {
        reservaService.salvar(reserva);
        return "redirect:/reservas";
    }

    @GetMapping("/reservas/editar/{id}")
    public String editarReserva(@PathVariable Long id, Model model) {
        return reservaService.buscarPorId(id)
                .map(reserva -> {
                    model.addAttribute("reserva", reserva);
                    return "formulario-reserva";
                })
                .orElse("redirect:/reservas");
    }

    @GetMapping("/reservas/excluir/{id}")
    public String excluirReserva(@PathVariable Long id) {
        reservaService.excluir(id);
        return "redirect:/reservas";
    }
}
```

### Pontos para destacar

- O controller concentra o fluxo principal do CRUD.
- A listagem usa `GET /reservas`.
- O cadastro e a edição usam o mesmo formulário.
- Quando um ID de edição não existe, o sistema redireciona para a lista sem quebrar a tela.
- A exclusão retorna para a listagem após remover a reserva.

---

## Tópico 2: Estratégia de Testes

### Pirâmide de testes adotada

1. Testes unitários para validar comportamento isolado.
2. Testes de integração para validar comunicação entre camadas.
3. Testes de ponta a ponta para validar o fluxo crítico do usuário.

### Distribuição proposta

| Camada | Percentual aproximado | Finalidade |
|---|---:|---|
| Unitários | 70% | Validar serviço e regras isoladas |
| Integração | 20% | Validar controller, repositório e contratos entre camadas |
| Ponta a ponta | 10% | Validar experiência real do usuário |

### Dependências de teste no Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Plugin de cobertura JaCoCo

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Critérios da estratégia

| Tipo | Critérios |
|---|---|
| Entrada | Build compilando, ambiente de teste definido e casos priorizados |
| Aceite | Pipeline em verde, sem falhas críticas nos fluxos CRUD e relatórios publicados |
| Saída | Regressão principal executada, bugs críticos resolvidos e evidências registradas |

---

## Tópico 3: Testes Manuais e Modelagem

### Campos principais do formulário

```html
<form th:action="@{/reservas/salvar}" th:object="${reserva}" method="post">
    <input type="hidden" th:field="*{id}">

    <div class="mb-3">
        <label for="nomeCliente" class="form-label">Nome do Cliente</label>
        <input type="text" id="nomeCliente" class="form-control" th:field="*{nomeCliente}" required>
    </div>

    <div class="mb-3">
        <label for="dataReserva" class="form-label">Data da Reserva</label>
        <input type="date" id="dataReserva" name="dataReserva" class="form-control"
               th:value="${reserva.dataReserva != null ? #temporals.format(reserva.dataReserva, 'yyyy-MM-dd') : ''}" required>
    </div>

    <div class="mb-3">
        <label for="quantidadePessoas" class="form-label">Quantidade de Pessoas</label>
        <input type="number" id="quantidadePessoas" class="form-control" th:field="*{quantidadePessoas}" min="1" required>
    </div>

    <div class="mb-3">
        <label for="status" class="form-label">Status</label>
        <select id="status" class="form-select" th:field="*{status}" required>
            <option value="">Selecione...</option>
            <option value="Confirmada">Confirmada</option>
            <option value="Pendente">Pendente</option>
        </select>
    </div>

    <div class="d-flex gap-2">
        <button type="submit" class="btn btn-success">Salvar</button>
        <a th:href="@{/reservas}" class="btn btn-secondary">Cancelar</a>
    </div>
</form>
```

### Casos de teste formais

| ID | Técnica | Cenário | Entrada | Resultado esperado | Prioridade |
|---|---|---|---|---|---|
| CT-01 | Particionamento de equivalência | Cadastrar reserva válida | Nome preenchido, data futura, qtd >= 1, status válido | Reserva salva e exibida na lista | Alta |
| CT-02 | Particionamento de equivalência | Nome vazio | `nomeCliente` vazio | Bloqueio no formulário | Alta |
| CT-03 | Valor limite | Quantidade mínima | `quantidadePessoas = 1` | Cadastro aceito | Alta |
| CT-04 | Valor limite | Quantidade abaixo do mínimo | `quantidadePessoas = 0` | Bloqueio no formulário | Alta |
| CT-05 | Tabela de decisão | Combinação de campos obrigatórios | Nome/data/qtd/status em combinações válidas e inválidas | Apenas combinação válida permite salvar | Alta |
| CT-06 | Transição de estados | Alterar status de Pendente para Confirmada | Reserva existente em status Pendente | Status alterado e persistido | Média |
| CT-07 | Particionamento de equivalência | Editar reserva inexistente | ID não encontrado | Redireciona para lista sem quebrar tela | Média |
| CT-08 | Particionamento de equivalência | Excluir reserva existente | ID válido | Reserva removida da listagem | Alta |

### Modelo de relatório de bug

| Campo | Conteúdo esperado |
|---|---|
| Bug ID | Identificador do defeito |
| Título | Resumo curto e objetivo |
| Descrição | O que aconteceu e o que era esperado |
| Severidade | Crítica, alta, média ou baixa |
| Prioridade | P1, P2, P3 ou P4 |
| Ambiente | Exemplo: Docker local, navegador e versão do Java |
| Passos para reprodução | Sequência objetiva para repetir o problema |
| Resultado atual | Comportamento observado |
| Resultado esperado | Comportamento correto |
| Evidências | Screenshot, vídeo curto ou log |
| Status | Aberto, em análise, em correção, resolvido, reaberto ou fechado |

---

## Tópico 4: Automação de Testes

### Ferramentas adotadas

1. JUnit 5 e Mockito para testes unitários.
2. Spring MockMvc para testes de integração web.
3. DataJpaTest e H2 para persistência.
4. Playwright para teste de ponta a ponta do fluxo crítico de cadastro.

### Cobertura automatizada criada

| Camada | Arquivo | Objetivo |
|---|---|---|
| Serviço | `ReservaServiceTest.java` | Validar salvar, listar, buscar e excluir |
| Web | `ReservaControllerWebTest.java` | Validar rotas, views, redirects e model |
| Repositório | `ReservaRepositoryTest.java` | Validar persistência com banco de teste |
| Ponta a ponta | `reserva-fluxo-critico.spec.js` | Validar cadastro e visualização na lista |

### Teste unitário de serviço

```java
@Test
@DisplayName("Deve salvar reserva chamando o repository")
void deveSalvarReserva() {
    Reserva entrada = criarReservaExemplo();
    when(reservaRepository.save(any(Reserva.class))).thenReturn(entrada);

    Reserva resultado = reservaService.salvar(entrada);

    assertEquals("Ana", resultado.getNomeCliente());
    verify(reservaRepository, times(1)).save(entrada);
}
```

### Teste de integração web

```java
@Test
@DisplayName("POST /reservas/salvar deve salvar e redirecionar")
void salvarReservaDeveRedirecionar() throws Exception {
    when(reservaService.salvar(any(Reserva.class))).thenReturn(criarReservaExemplo());

    mockMvc.perform(post("/reservas/salvar")
                    .param("nomeCliente", "Carla")
                    .param("dataReserva", "2026-08-10")
                    .param("quantidadePessoas", "4")
                    .param("status", "Confirmada"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservas"));

    verify(reservaService, times(1)).salvar(any(Reserva.class));
}
```

### Teste para edição com ID inexistente

```java
@Test
@DisplayName("GET /reservas/editar/{id} inexistente deve redirecionar")
void editarReservaComIdInexistente() throws Exception {
    when(reservaService.buscarPorId(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/reservas/editar/999"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservas"));
}
```

### Teste de repositório

```java
@Test
@DisplayName("Deve salvar e recuperar reserva no banco de teste")
void deveSalvarEBuscarReserva() {
    Reserva reserva = new Reserva();
    reserva.setNomeCliente("Diego");
    reserva.setDataReserva(LocalDate.of(2026, 9, 15));
    reserva.setQuantidadePessoas(5);
    reserva.setStatus("Confirmada");

    Reserva salva = reservaRepository.save(reserva);
    Optional<Reserva> buscada = reservaRepository.findById(salva.getId());

    assertTrue(buscada.isPresent());
    assertEquals("Diego", buscada.get().getNomeCliente());
}
```

### Teste de ponta a ponta com Playwright

```javascript
const { test, expect } = require('@playwright/test');

test('Fluxo critico: cadastrar e visualizar reserva na lista', async ({ page }) => {
  await page.goto('/');

  await page.getByRole('link', { name: 'Nova Reserva' }).click();

  await page.locator('#nomeCliente').fill('Cliente E2E');
  await page.locator('#dataReserva').fill('2026-12-20');
  await page.locator('#quantidadePessoas').fill('3');
  await page.locator('#status').selectOption('Confirmada');

  await page.getByRole('button', { name: 'Salvar' }).click();
  await expect(page).toHaveURL(/\/reservas$/);

  await expect(page.getByText('Cliente E2E')).toBeVisible();
});
```

### Comandos de execução

```bash
mvn clean test
```

```bash
docker compose up --build
```

```bash
cd e2e/playwright
npm install
npx playwright install
npm run test:e2e
```

---

## Tópico 5: Uso de IA nos Testes

### Finalidade desta etapa

Nesta etapa, a inteligência artificial foi usada como ferramenta de apoio para ampliar a revisão dos cenários de teste, gerar massa de dados, pensar em exceções e conferir se os fluxos críticos estavam cobertos.

### Prompts sugeridos para gerar massa de teste

1. Gerar exemplos de reservas válidas com nome do cliente, data, quantidade de pessoas e status.
2. Sugerir casos negativos para campos obrigatórios do formulário.
3. Criar cenários de regressão para cadastro, listagem, edição e exclusão de reservas.
4. Propor situações de exceção, como tentativa de edição de uma reserva inexistente.

### Limitações da IA

1. A IA pode sugerir regras que não existem no sistema.
2. A IA pode repetir cenários parecidos com nomes diferentes.
3. A IA pode ignorar detalhes do domínio se o contexto informado for incompleto.
4. A IA não substitui a validação humana nem a análise do comportamento real da aplicação.

### Onde a validação humana é indispensável

1. Confirmar se o cenário sugerido faz sentido para o Sistema de Reservas.
2. Remover casos redundantes ou fora do escopo.
3. Garantir que os testes reflitam as regras realmente implementadas.
4. Priorizar os fluxos de maior risco: cadastro, listagem, edição e exclusão.
5. Revisar casos inesperados, como editar uma reserva inexistente, para evitar quebra de tela.

### Exemplo de cenário revisado e automatizado

```java
@Test
@DisplayName("GET /reservas/editar/{id} inexistente deve redirecionar")
void editarReservaComIdInexistente() throws Exception {
    when(reservaService.buscarPorId(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/reservas/editar/999"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservas"));
}
```

### Mensagem principal

- A IA acelerou a identificação de casos positivos, negativos e de regressão.
- A decisão final sobre o que testar continuou baseada no comportamento real do sistema.
- O teste de ID inexistente mostra cuidado com situação inesperada e melhora a robustez da aplicação.

---

## Tópico 6: Pipeline de Qualidade

### Estrutura geral

```yaml
name: CI-QA

on:
  push:
    branches: [ "main", "master" ]
  pull_request:
    branches: [ "main", "master" ]
```

### Job de build, testes e qualidade

```yaml
build-test-quality:
  runs-on: ubuntu-latest

  steps:
    - name: Checkout
      uses: actions/checkout@v5

    - name: Setup Java
      uses: actions/setup-java@v4
      with:
        distribution: temurin
        java-version: '17'
        cache: maven

    - name: Build and Test
      run: mvn -B clean test

    - name: Upload Surefire Reports
      if: always()
      uses: actions/upload-artifact@v7
      with:
        name: surefire-reports
        path: target/surefire-reports

    - name: Upload JaCoCo Report
      if: always()
      uses: actions/upload-artifact@v7
      with:
        name: jacoco-report
        path: target/site/jacoco

    - name: Quality Gate Placeholder
      run: 'echo "Quality gate basico executado: build + testes + cobertura publicados"'
```

### Job de testes de ponta a ponta

```yaml
e2e-playwright:
  runs-on: ubuntu-latest
  needs: build-test-quality

  steps:
    - name: Checkout
      uses: actions/checkout@v5

    - name: Setup Java
      uses: actions/setup-java@v4
      with:
        distribution: temurin
        java-version: '17'
        cache: maven

    - name: Setup Node
      uses: actions/setup-node@v4
      with:
        node-version: '20'

    - name: Package Application
      run: mvn -B -DskipTests package

    - name: Install E2E Dependencies
      working-directory: e2e/playwright
      run: npm install

    - name: Install Playwright Browsers
      working-directory: e2e/playwright
      run: npx playwright install --with-deps

    - name: Start Application (CI Profile)
      run: |
        nohup java -jar target/*.jar --spring.profiles.active=ci > app.log 2>&1 &

    - name: Wait for Application
      shell: bash
      run: |
        for i in {1..60}; do
          if curl -fsS http://localhost:8080/reservas > /dev/null; then
            echo "Aplicacao pronta para E2E"
            exit 0
          fi
          sleep 2
        done
        echo "Aplicacao nao respondeu a tempo"
        exit 1

    - name: Run E2E Tests
      working-directory: e2e/playwright
      run: npm run test:e2e

    - name: Upload Playwright Report
      if: always()
      uses: actions/upload-artifact@v7
      with:
        name: playwright-report
        path: e2e/playwright/playwright-report

    - name: Upload Application Log
      if: always()
      uses: actions/upload-artifact@v7
      with:
        name: app-log
        path: app.log
```

### O que o pipeline faz

1. Baixa o código do repositório.
2. Configura Java 17 com cache Maven.
3. Compila o projeto.
4. Executa testes Java.
5. Publica relatórios Surefire.
6. Publica relatório de cobertura JaCoCo.
7. Configura Node e Playwright.
8. Empacota a aplicação.
9. Sobe a aplicação em perfil de integração contínua.
10. Executa o fluxo crítico de ponta a ponta.
11. Publica relatório Playwright e log da aplicação.

### Benefícios

- Padroniza a validação fora da máquina do desenvolvedor.
- Gera evidências automáticas.
- Ajuda a encontrar regressões mais cedo.
- Melhora a rastreabilidade de cada alteração.

---

## Tópico 7: Testes Não Funcionais

### Endpoint relacionado no controller

```java
@GetMapping("/reservas")
public String listarReservas(Model model) {
    model.addAttribute("reservas", reservaService.listarTodas());
    return "lista-reservas";
}
```

### Cenário inicial proposto

| Item | Valor |
|---|---|
| Tipo de teste | Performance/carga inicial |
| Ferramenta proposta | Apache JMeter |
| Endpoint alvo | `GET /reservas` |
| Usuários virtuais | 30 |
| Ramp-up | 30 segundos |
| Duração | 3 minutos |
| Meta de latência p95 | Até 800 ms |
| Meta de erro | Menor que 1% |

### Passos de execução

1. Subir a aplicação com Docker.
2. Criar plano no JMeter com Thread Group, HTTP Request e Listeners.
3. Executar o teste.
4. Exportar relatório HTML.
5. Registrar os resultados no acompanhamento de qualidade.

### Critérios de aprovação

1. p95 dentro da meta.
2. Taxa de erro abaixo da meta.
3. Sem degradação severa de resposta ao longo do teste.

### Evolução de segurança

Como segundo ciclo, executar baseline de segurança com OWASP ZAP, usando varredura passiva e ativa leve para identificar vulnerabilidades comuns em aplicação web.

---

## Tópico 8: Métricas e Governança

### Indicadores principais

| Bloco | Indicador | Fonte ou cálculo |
|---|---|---|
| Qualidade de código | Cobertura de testes | JaCoCo |
| Qualidade de código | Falhas por pipeline | GitHub Actions |
| Qualidade de produto | Densidade de defeitos | Bugs confirmados / tamanho funcional entregue |
| Qualidade de produto | Taxa de falhas em produção | Incidentes / período |
| Qualidade de produto | Tempo médio de correção | Média de tempo entre abertura e resolução |
| Efetividade dos testes | Regressão capturada antes da entrega | Defeitos encontrados antes da liberação |
| Efetividade dos testes | Taxa de sucesso dos testes de ponta a ponta | Execuções bem-sucedidas / execuções totais |

### Trechos do pipeline que geram evidências

```yaml
- name: Upload Surefire Reports
  if: always()
  uses: actions/upload-artifact@v7
  with:
    name: surefire-reports
    path: target/surefire-reports

- name: Upload JaCoCo Report
  if: always()
  uses: actions/upload-artifact@v7
  with:
    name: jacoco-report
    path: target/site/jacoco

- name: Upload Playwright Report
  if: always()
  uses: actions/upload-artifact@v7
  with:
    name: playwright-report
    path: e2e/playwright/playwright-report

- name: Quality Gate Placeholder
  run: 'echo "Quality gate basico executado: build + testes + cobertura publicados"'
```

### Rotina de governança

1. Daily de qualidade: acompanhar falhas novas e riscos.
2. Review semanal: observar tendências e ações corretivas.
3. Gate de release: sem bug crítico aberto e pipeline verde.

### Política mínima de qualidade

1. Build quebrado bloqueia merge.
2. Bug crítico bloqueia release.
3. Regressão dos fluxos críticos é obrigatória em cada release.

---

## Conclusão

### Síntese da entrega

- O projeto recebeu uma estrutura de qualidade organizada em camadas.
- Os testes unitários validam comportamento isolado.
- Os testes de integração validam a comunicação entre controller, serviço e repositório.
- O teste de ponta a ponta valida o fluxo principal do usuário.
- O pipeline executa validações automaticamente em ambiente padronizado.
- Os relatórios e artefatos servem como evidências.
- As métricas ajudam a acompanhar evolução, confiabilidade e riscos.

### Mensagem final

A principal contribuição foi transformar testes e qualidade em um processo contínuo, com mais confiabilidade, previsibilidade e menor risco de regressão para o Sistema de Reservas.
