# Trechos de Código Referenciados em `apresentacao.md`

## Trecho 1
Arquivo na árvore do repositório: `pom.xml`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/pom.xml - dependências e plugins principais do projeto
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

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
</dependencies>
```

## Trecho 2
Arquivo na árvore do repositório: `src/main/resources/templates/lista-reservas.html`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/templates/lista-reservas.html - template da listagem principal
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h3 mb-0">Sistema de Reservas</h1>
    <a th:href="@{/reservas/nova}" class="btn btn-primary">Nova Reserva</a>
</div>

<tbody>
<tr th:if="${#lists.isEmpty(reservas)}">
    <td colspan="6" class="text-center text-muted">Nenhuma reserva cadastrada.</td>
</tr>
<tr th:each="reserva : ${reservas}">
    <td th:text="${reserva.id}"></td>
    <td th:text="${reserva.nomeCliente}"></td>
    <td th:text="${reserva.dataReserva}"></td>
    <td th:text="${reserva.quantidadePessoas}"></td>
    <td th:text="${reserva.status}"></td>
    <td class="text-center">
        <a th:href="@{/reservas/editar/{id}(id=${reserva.id})}" class="btn btn-warning btn-sm">Editar</a>
        <a th:href="@{/reservas/excluir/{id}(id=${reserva.id})}"
           class="btn btn-danger btn-sm"
           onclick="return confirm('Deseja realmente excluir esta reserva?')">Excluir</a>
    </td>
</tr>
</tbody>
```

## Trecho 3
Arquivo na árvore do repositório: `src/main/resources/templates/formulario-reserva.html`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/templates/formulario-reserva.html - template de cadastro e edição
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
</form>
```

## Trecho 4
Arquivo na árvore do repositório: `src/main/java/com/exemplo/reservas/model/Reserva.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/java/com/exemplo/reservas/model/Reserva.java - classe de domínio Reserva
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;
    private LocalDate dataReserva;
    private int quantidadePessoas;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
```

## Trecho 5
Arquivo na árvore do repositório: `src/main/java/com/exemplo/reservas/repository/ReservaRepository.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/java/com/exemplo/reservas/repository/ReservaRepository.java - interface de persistência
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
```

## Trecho 6
Arquivo na árvore do repositório: `src/main/java/com/exemplo/reservas/service/ReservaService.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/java/com/exemplo/reservas/service/ReservaService.java - camada de serviço
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva salvar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public void excluir(Long id) {
        reservaRepository.deleteById(id);
    }
}
```

## Trecho 7
Arquivo na árvore do repositório: `src/main/java/com/exemplo/reservas/controller/ReservaController.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/java/com/exemplo/reservas/controller/ReservaController.java - controller principal da aplicação
@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "lista-reservas";
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
}
```

## Trecho 8
Arquivo na árvore do repositório: `src/main/resources/application.properties`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/application.properties - configuração de persistência local
spring.datasource.url=jdbc:mysql://localhost:3306/reservas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Trecho 9
Arquivo na árvore do repositório: `src/main/resources/application-ci.properties`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/application-ci.properties - configuração de persistência para CI
spring.datasource.url=jdbc:h2:mem:reservas_ci;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

## Trecho 10
Arquivo na árvore do repositório: `.github/workflows/ci-qa.yml`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/.github/workflows/ci-qa.yml - automação de qualidade no GitHub Actions
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
```

## Trecho 11
Arquivo na árvore do repositório: `src/test/java/com/exemplo/reservas/service/ReservaServiceTest.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/test/java/com/exemplo/reservas/service/ReservaServiceTest.java - teste unitário da camada de serviço
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Deve salvar reserva chamando o repository")
    void deveSalvarReserva() {
        Reserva entrada = criarReservaExemplo();
        when(reservaRepository.save(any(Reserva.class))).thenReturn(entrada);

        Reserva resultado = reservaService.salvar(entrada);

        assertEquals("Ana", resultado.getNomeCliente());
        verify(reservaRepository, times(1)).save(entrada);
    }
}
```

## Trecho 12
Arquivo na árvore do repositório: `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java - teste da camada web
@WebMvcTest(ReservaController.class)
class ReservaControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Test
    @DisplayName("GET /reservas deve renderizar lista-reservas")
    void listarReservasDeveRenderizarLista() throws Exception {
        when(reservaService.listarTodas()).thenReturn(List.of(criarReservaExemplo()));

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-reservas"))
                .andExpect(model().attributeExists("reservas"));
    }
}
```

## Trecho 13
Arquivo na árvore do repositório: `e2e/playwright/tests/reserva-fluxo-critico.spec.js`

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/e2e/playwright/tests/reserva-fluxo-critico.spec.js - teste E2E do fluxo principal
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
