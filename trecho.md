# Trechos de Código — Material de Apoio à Apresentação

> Este arquivo reúne os trechos de código citados no roteiro `apresentacao.md`.
> Ele serve como apoio rápido durante a apresentação: cada trecho indica o arquivo de
> origem, a finalidade e a relação com a fala do roteiro. Não substitui o roteiro principal.

---

## Seção 2 — Orientação a Objetos II

### 2.1 — Ponto de entrada da aplicação

- **Arquivo:** `src/main/java/com/exemplo/reservas/SistemaReservasApplication.java`
- **Finalidade:** método `main` e anotação que liga o motor do Spring Boot.
- **Relação com a fala:** usado quando explico que `@SpringBootApplication` sobe toda a aplicação com pouco código.

```java
@SpringBootApplication
public class SistemaReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaReservasApplication.class, args);
    }
}
```

### 2.2 — Entidade `Reserva` (encapsulamento, tipagem e ORM)

- **Arquivo:** `src/main/java/com/exemplo/reservas/model/Reserva.java`
- **Finalidade:** representa a tabela de reservas; mostra atributos `private`, getters/setters e anotações JPA.
- **Relação com a fala:** base do trecho sobre encapsulamento, tipos (`Long`, `LocalDate`, `int`, `String`) e mapeamento objeto-relacional (`@Entity`, `@Id`, `@GeneratedValue`).

```java
@Entity // Marca a classe como tabela no banco
public class Reserva {

    @Id // Define a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento no MySQL
    private Long id;

    private String nomeCliente;
    private LocalDate dataReserva;
    private int quantidadePessoas;
    private String status;

    public Reserva() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // ... demais getters e setters seguem o mesmo padrão de encapsulamento
}
```

### 2.3 — Repositório (generics e herança de interface)

- **Arquivo:** `src/main/java/com/exemplo/reservas/repository/ReservaRepository.java`
- **Finalidade:** interface que estende `JpaRepository`; ganha métodos de acesso ao banco sem implementação manual.
- **Relação com a fala:** usado para falar de generics (`<Reserva, Long>`), herança de interface e abstração.

```java
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
```

### 2.4 — Serviço (injeção de dependência, coleções e `Optional`)

- **Arquivo:** `src/main/java/com/exemplo/reservas/service/ReservaService.java`
- **Finalidade:** camada de lógica; recebe o repositório por construtor e expõe as operações.
- **Relação com a fala:** base do trecho sobre injeção de dependência (`private final` + construtor), `List<Reserva>` e `Optional<Reserva>`.

```java
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    // Injeta o repository para acessar o banco
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

### 2.5 — Controlador (rotas web e tratamento elegante do `Optional`)

- **Arquivo:** `src/main/java/com/exemplo/reservas/controller/ReservaController.java`
- **Finalidade:** recebe requisições, mapeia rotas (`@GetMapping`, `@PostMapping`, `@PathVariable`) e direciona para as telas.
- **Relação com a fala:** usado no fechamento da parte de POO, ao mostrar o uso de `map`/`orElse` no método de edição.

```java
// Salva nova reserva ou edicao
@PostMapping("/reservas/salvar")
public String salvarReserva(@ModelAttribute Reserva reserva) {
    reservaService.salvar(reserva);
    return "redirect:/reservas";
}

// Carrega uma reserva existente no formulario
@GetMapping("/reservas/editar/{id}")
public String editarReserva(@PathVariable Long id, Model model) {
    return reservaService.buscarPorId(id)
            .map(reserva -> {
                model.addAttribute("reserva", reserva);
                return "formulario-reserva";
            })
            .orElse("redirect:/reservas");
}
```

### 2.6 — Configuração do caminho até o banco (`application.properties`)

- **Arquivo:** `src/main/resources/application.properties`
- **Finalidade:** define o endereço do MySQL (datasource), o driver JDBC e o comportamento do JPA/Hibernate.
- **Relação com a fala:** apoia o trecho sobre rotas, requisições e o caminho até o banco, mostrando onde a conexão é configurada (fora do código Java) e como o `ddl-auto=update` cria as tabelas automaticamente.

```properties
# Endereco do banco: protocolo, host, porta e nome do schema
spring.datasource.url=jdbc:mysql://localhost:3306/reservas_db
spring.datasource.username=root
# (senha definida no proprio application.properties)
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate cria/atualiza as tabelas a partir das entidades
spring.jpa.hibernate.ddl-auto=update
# Imprime no console o SQL gerado pelo ORM
spring.jpa.show-sql=true
```

> **Caminho do dado:** controller → service → repository → Spring Data JPA → Hibernate → driver JDBC → MySQL.
> No Docker, o arquivo `application-docker.properties` muda apenas o host de `localhost` para `db`.

---

## Seção 3 — Testes e Qualidade

### 3.1 — Fase 4: Teste unitário do serviço com Mockito

- **Arquivo:** `src/test/java/com/exemplo/reservas/service/ReservaServiceTest.java`
- **Finalidade:** valida a lógica do serviço de forma isolada, usando um repositório simulado (`@Mock`).
- **Relação com a fala:** mostra como a injeção de dependência permite testar com mocks (`@Mock`, `@InjectMocks`).

```java
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

### 3.2 — Fase 4: Teste de integração web com `@WebMvcTest` e `MockMvc`

- **Arquivo:** `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java`
- **Finalidade:** simula requisições HTTP contra o controlador e valida rotas, views e redirecionamentos.
- **Relação com a fala:** usado para falar da camada de integração e do tratamento do caminho de exceção (id inexistente).

```java
@WebMvcTest(ReservaController.class)
class ReservaControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Test
    @DisplayName("GET /reservas/editar/{id} inexistente deve redirecionar")
    void editarReservaComIdInexistente() throws Exception {
        when(reservaService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservas/editar/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservas"));
    }
}
```

### 3.3 — Fase 4: Teste de persistência com `@DataJpaTest` (banco H2)

- **Arquivo:** `src/test/java/com/exemplo/reservas/repository/ReservaRepositoryTest.java`
- **Finalidade:** salva e recupera uma reserva em banco em memória para validar o mapeamento objeto-relacional.
- **Relação com a fala:** usado ao explicar a camada de persistência dos testes.

```java
@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

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
}
```

### 3.4 — Fase 4: Teste End-to-End com Playwright (fluxo crítico)

- **Arquivo:** `e2e/playwright/tests/reserva-fluxo-critico.spec.js`
- **Finalidade:** abre um navegador real, cadastra uma reserva e confere se ela aparece na lista.
- **Relação com a fala:** usado ao explicar o topo da pirâmide, o teste mais próximo do usuário final.

```javascript
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

### 3.5 — Fase 6: Pipeline de CI/CD (GitHub Actions)

- **Arquivo:** `.github/workflows/ci-qa.yml`
- **Finalidade:** roda build, testes Java e testes E2E a cada push/pull request, publicando relatórios.
- **Relação com a fala:** usado ao explicar a Fase 6 (pipeline de qualidade) e a publicação do relatório JaCoCo.

```yaml
jobs:
  build-test-quality:
    runs-on: ubuntu-latest
    steps:
      - name: Build and Test
        run: mvn -B clean test

      - name: Upload JaCoCo Report
        if: always()
        uses: actions/upload-artifact@v7
        with:
          name: jacoco-report
          path: target/site/jacoco

  e2e-playwright:
    runs-on: ubuntu-latest
    needs: build-test-quality
    steps:
      - name: Run E2E Tests
        working-directory: e2e/playwright
        run: npm run test:e2e
```
