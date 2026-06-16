# 1. Parte Geral: Visão Geral e Funcionalidades

Ao apresentar este projeto, eu começaria explicando que o sistema resolve um problema muito comum em ambientes acadêmicos e corporativos: organizar o cadastro e o acompanhamento de reservas de forma simples, centralizada e com baixo atrito para o usuário. Em vez de controlar solicitações manualmente, a aplicação oferece um fluxo direto para registrar, visualizar, editar e excluir reservas, sempre mantendo os dados persistidos em banco. A base tecnológica escolhida mostra um recorte didático, mas ao mesmo tempo muito representativo de uma aplicação web real em Java: Spring Boot para estruturar a aplicação, Spring MVC para a camada web, Thymeleaf para renderização do HTML no servidor e Spring Data JPA com Hibernate para a persistência.

Esse objetivo aparece com clareza já na composição das dependências do projeto, porque o `pom.xml` revela que a aplicação foi pensada para cobrir interface web, persistência e qualidade de software dentro do mesmo ecossistema.

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

Do ponto de vista do usuário final, a principal funcionalidade é manter uma agenda de reservas. A tela inicial redireciona para a listagem, a listagem mostra os registros existentes, o botão de nova reserva abre o formulário, o mesmo formulário serve tanto para criação quanto para edição, e a listagem também expõe a exclusão de um registro. Esse comportamento não está apenas na lógica do backend; ele também fica evidente no template que organiza a experiência do usuário, inclusive prevendo o cenário de não haver nenhuma reserva cadastrada e disponibilizando ações explícitas de editar e excluir.

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

Na prática, as regras de negócio implementadas são diretas e coerentes com a proposta do sistema. Cada reserva registra quem é o cliente, a data da reserva, a quantidade de pessoas e o status, que no formulário é conduzido por opções como “Confirmada” e “Pendente”. Também existe uma pequena regra implícita importante: o sistema reutiliza o mesmo formulário para cadastrar e editar, e essa decisão reduz duplicação na interface e no fluxo de processamento. Isso aparece no uso do campo oculto `id`, que permite ao backend distinguir quando está lidando com um novo registro e quando está atualizando uma reserva já existente.

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

# 2. Parte Técnica: Programação Orientada a Objetos II (Java, Endpoints e Persistência)

Na parte técnica, eu apresentaria o projeto como um exemplo de arquitetura em camadas. A entidade `Reserva` representa o domínio, o repositório encapsula o acesso ao banco, o serviço concentra a lógica de aplicação e o controller faz a ponte entre a requisição HTTP e a resposta entregue ao usuário. Essa organização já demonstra abstração, porque cada classe assume uma responsabilidade específica, e também favorece manutenção, testes e evolução futura.

Quando eu fosse tratar dos pilares de POO, começaria pelo encapsulamento. A classe `Reserva` mantém seus atributos privados e expõe o acesso por meio de getters e setters, impedindo que outras partes do sistema manipulem diretamente o estado interno do objeto. Ao mesmo tempo, ela abstrai o conceito de uma reserva em uma estrutura coesa, reunindo identidade, data, quantidade de pessoas e status em uma única entidade de domínio.

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

Em seguida, eu destacaria que herança e polimorfismo aparecem menos como criação manual de subclasses e mais como uso consciente das abstrações do ecossistema Spring. O melhor exemplo é o repositório: a interface `ReservaRepository` herda o comportamento pronto de `JpaRepository<Reserva, Long>`, recebendo operações como `save`, `findAll`, `findById` e `deleteById` sem que o projeto precise reimplementar essas rotinas. Isso é herança no nível de interface e também polimorfismo, porque a aplicação trabalha contra o contrato `ReservaRepository`, enquanto em tempo de execução o Spring injeta uma implementação concreta gerada dinamicamente.

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/java/com/exemplo/reservas/repository/ReservaRepository.java - interface de persistência
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
```

O mesmo raciocínio vale para padrões de projeto. O sistema usa com clareza o padrão Repository, já que separa a persistência da lógica de aplicação. Também adota o padrão Service Layer, pois `ReservaService` funciona como intermediário entre controller e repositório. Além disso, há forte presença de Injeção de Dependência, uma prática alinhada ao princípio de inversão de dependência: as classes recebem suas colaborações pelo construtor em vez de criarem dependências diretamente. Isso reduz acoplamento e melhora testabilidade.

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

Ao explicar a camada web e os endpoints, eu mostraria que a entrada principal do sistema é o `ReservaController`. É nele que as rotas HTTP são definidas e transformadas em ações de navegação e manipulação dos dados. O endpoint `GET /reservas` consulta o serviço, insere a lista no `Model` e devolve a view `lista-reservas`. O endpoint `POST /reservas/salvar` recebe o objeto preenchido pelo formulário com `@ModelAttribute`, delega a persistência ao serviço e redireciona para a listagem. Já o fluxo de edição demonstra uma boa prática importante: o controller tenta buscar o registro pelo identificador e só entrega o formulário se a reserva existir; caso contrário, redireciona o usuário de volta para a listagem.

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

Nesse ponto da apresentação, eu detalharia o fluxo completo dos dados: o navegador envia uma requisição para um endpoint do controller; o controller converte os dados da requisição em objeto Java ou em parâmetro simples; o serviço recebe a chamada e centraliza a operação; o repositório conversa com o banco; e o resultado retorna para o controller, que decide se entrega uma view HTML ou se redireciona o usuário. Mesmo sendo uma aplicação pequena, esse fluxo já reproduz o ciclo clássico de uma aplicação corporativa baseada em MVC.

Na parte de persistência e banco de dados, o ponto mais importante é mostrar que o projeto usa Spring Data JPA com Hibernate como implementação ORM. A entidade `Reserva` é marcada com `@Entity`, o identificador usa `@Id` e `@GeneratedValue(strategy = GenerationType.IDENTITY)`, e a configuração da aplicação informa a URL JDBC, credenciais e a política de atualização do esquema com `spring.jpa.hibernate.ddl-auto=update`. Isso significa que a camada de persistência trabalha orientada a objetos, enquanto o Hibernate faz a tradução para operações SQL compatíveis com o banco relacional.

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/application.properties - configuração de persistência local
spring.datasource.url=jdbc:mysql://localhost:3306/reservas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Também vale destacar um aspecto arquitetural importante para demonstração acadêmica: o projeto separa o perfil de execução local do perfil de CI. No ambiente normal, a aplicação usa MySQL; no pipeline automatizado, usa H2 em memória para simplificar a execução dos testes e do fluxo E2E. Isso mostra preocupação com portabilidade e automação da qualidade, sem exigir que a infraestrutura de testes dependa de um banco externo.

```java
// /home/runner/work/projetodejava_reservas/projetodejava_reservas/src/main/resources/application-ci.properties - configuração de persistência para CI
spring.datasource.url=jdbc:h2:mem:reservas_ci;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

# 3. Parte Técnica: Testes e Qualidade de Software (QA)

Na parte de QA, eu apresentaria o projeto como um sistema pequeno, mas com estratégia de testes bem distribuída entre camadas. Há testes unitários na camada de serviço, em que o comportamento da lógica é verificado de forma isolada com uso de mocks; há testes web com `MockMvc`, que validam a camada MVC sem subir a aplicação inteira; há testes de repositório com `@DataJpaTest`, que exercitam a persistência em um contexto JPA real; e ainda existe um teste end-to-end com Playwright, cobrindo o fluxo crítico do ponto de vista do usuário. Essa combinação é interessante academicamente porque mostra diferentes níveis da pirâmide de testes coexistindo no mesmo repositório.

As ferramentas e frameworks ficam claros tanto no `pom.xml` quanto nos próprios arquivos de teste. O `spring-boot-starter-test` traz JUnit 5 para estrutura e execução, Mockito para criação de mocks e verificação de interações, Assert/JUnit Assertions para as validações, além de suporte do Spring Test e do MockMvc para testes web. O H2 é usado como banco em memória nos cenários de persistência e CI, o JaCoCo gera cobertura de testes e o Playwright cobre o fluxo E2E da interface. Inclusive, o pipeline de CI explicita que a qualidade esperada do projeto envolve build, testes automatizados e publicação de artefatos de relatório.

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

Para a análise prática de qualidade, eu escolheria o teste unitário do serviço, porque ele evidencia com muita clareza a separação entre comportamento esperado e dependência externa. No exemplo abaixo, o repositório é mockado com Mockito, a chamada `when(...).thenReturn(...)` define o comportamento controlado da dependência, o método real do serviço é executado e depois os asserts e verificações confirmam tanto o resultado observado quanto a colaboração correta com o repositório. Em outras palavras, o teste não depende do banco e valida se a camada de serviço realmente delega a gravação de forma correta.

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

Ao comentar esse trecho em uma apresentação, eu diria que o uso de `@Mock` impede acesso real ao banco, `@InjectMocks` monta o serviço com a dependência simulada, `assertEquals("Ana", resultado.getNomeCliente())` comprova que o objeto retornado é o esperado e `verify(reservaRepository, times(1)).save(entrada)` garante que houve exatamente uma chamada ao método de persistência. Ou seja, o teste verifica resultado e interação, o que é muito importante em camadas intermediárias como a de serviço.

Se eu quisesse ampliar a discussão, mostraria que a camada web também foi testada de maneira direcionada. O teste a seguir sobe apenas o contexto MVC do `ReservaController`, substitui o serviço por um mock e valida status HTTP, nome da view e presença dos atributos enviados para renderização. Isso prova que o endpoint não está apenas “existindo”, mas que ele realmente atende ao contrato esperado pela interface.

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

Por fim, eu encerraria a parte de qualidade destacando que o projeto também possui uma validação funcional ponta a ponta com Playwright. Esse tipo de teste é relevante porque sai da visão interna do código e verifica o comportamento percebido pelo usuário: abrir o sistema, navegar até o formulário, preencher os campos, salvar e confirmar que a reserva aparece na listagem. Com isso, o projeto cobre desde unidades isoladas até o fluxo real da aplicação.

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

Como fechamento da apresentação, eu reforçaria que este repositório é tecnicamente valioso porque demonstra, em escala enxuta, vários elementos centrais de desenvolvimento backend moderno com Java: modelagem orientada a objetos, arquitetura em camadas, uso de framework MVC, persistência com ORM, testes em diferentes níveis e automação de qualidade no pipeline. Mesmo sendo um sistema simples, ele oferece material suficiente para discutir fundamentos de POO II, ciclo de uma requisição web, integração com banco de dados e práticas reais de QA de forma objetiva e bem conectada ao código-fonte.
