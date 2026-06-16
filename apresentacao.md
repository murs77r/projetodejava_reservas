# Roteiro de Apresentação — Sistema de Reservas

> Este arquivo é um roteiro de fala. A ideia é que ele seja lido em voz alta, com naturalidade,
> como uma apresentação acadêmica. Os trechos de código citados ao longo do texto estão reunidos
> no arquivo `trecho.md`, para consulta rápida durante a apresentação.

---

## Parte 1 — Visão Geral do Sistema

Bom dia a todos. Nesta apresentação, eu vou mostrar o nosso **Sistema de Reservas**, um projeto
que desenvolvemos em grupo, e que foi pensado para resolver um problema bem concreto: o de
organizar reservas de salas de forma simples, confiável e sem depender de papel, planilha ou
controle manual.

Começando pelo contexto, a ideia central do sistema é permitir que uma pessoa registre uma reserva
informando quem é o cliente, qual a data, quantas pessoas vão participar e qual o status daquela
reserva — se ela está confirmada ou ainda pendente. A partir disso, o sistema mantém uma lista
organizada de todas as reservas, que pode ser consultada, alterada ou atualizada a qualquer momento.

O problema que a gente quis resolver é justamente esse: em muitos lugares, o controle de reservas
ainda é feito de maneira informal, o que gera confusão, agendamentos duplicados e perda de
informação. Nosso objetivo foi entregar uma aplicação web enxuta, mas funcional, que centralize
esse controle em um único lugar.

Sobre o fluxo principal de uso, ele é bem direto. Quando o usuário acessa o sistema, ele cai
diretamente na tela de listagem das reservas. A partir dali, ele tem quatro ações possíveis: pode
**cadastrar** uma nova reserva, pode **visualizar** as que já existem, pode **editar** uma reserva
já cadastrada e pode **excluir** o que não for mais necessário. Na prática, isso cobre o ciclo
completo daquilo que chamamos de CRUD — criar, ler, atualizar e remover.

E aqui eu queria, antes de entrar na teoria, fazer uma **demonstração ao vivo**, passo a passo, para
que vocês vejam o sistema funcionando de verdade. Vou conduzir a navegação pela própria tela.

[neste momento, abrir o navegador em `http://localhost:8080`]

**Passo 1 — A tela inicial.** Repare que, assim que eu acesso a aplicação, ela me leva direto para a
**lista de reservas**. Eu não preciso navegar por menu nenhum: o sistema entende que a primeira coisa
que o usuário quer é ver o que já está cadastrado. Essa é a tela central de tudo. No topo dela, à
direita, existe um botão azul escrito **"Nova Reserva"**, e logo abaixo temos uma tabela com as
colunas ID, Cliente, Data, Quantidade de Pessoas, Status e Ações. Se ainda não houver nenhuma reserva,
a própria tabela mostra a mensagem "Nenhuma reserva cadastrada", para não deixar a tela vazia sem
explicação.

**Passo 2 — Cadastrar uma nova reserva.** Agora eu vou clicar no botão "Nova Reserva". [clicar no
botão] Veja que o sistema me leva para um **formulário** com quatro campos: o nome do cliente, a data
da reserva, a quantidade de pessoas e o status. Eu vou preencher como exemplo: no nome, coloco
"Maria"; na data, escolho uma data no calendário; na quantidade, digito "4"; e no status, eu abro a
lista e seleciono "Confirmada". Reparem que alguns campos têm validação: a quantidade de pessoas, por
exemplo, não aceita um valor menor que 1.

**Passo 3 — Salvar e ver o resultado.** Vou clicar em **"Salvar"**. [clicar em Salvar] Olhem o que
aconteceu: o sistema gravou a reserva e me trouxe **de volta para a lista**, e agora a reserva da
"Maria" aparece na tabela, já com um ID gerado automaticamente. Esse ID não fui eu que digitei — foi
o próprio sistema que atribuiu, e isso vai ser importante quando eu explicar a parte do código.

**Passo 4 — Editar uma reserva existente.** Na linha da reserva, do lado direito, existe um botão
amarelo **"Editar"**. Vou clicar nele. [clicar em Editar] Repare que o sistema reabriu o **mesmo
formulário**, mas desta vez **já preenchido** com os dados da "Maria". Eu vou, por exemplo, mudar o
status de "Confirmada" para "Pendente" e salvar de novo. [salvar] E, de volta na lista, o status já
aparece atualizado. Aqui vale destacar uma decisão de projeto interessante: o mesmo formulário serve
tanto para criar quanto para editar.

**Passo 5 — Excluir uma reserva.** Por fim, ainda na linha da reserva, existe um botão vermelho
**"Excluir"**. Quando eu clico nele [clicar em Excluir], o sistema me pede uma **confirmação** antes
de apagar, justamente para evitar exclusões por engano. Se eu confirmar, a reserva some da lista.

Com esses cinco passos, vocês acabaram de ver o ciclo completo do sistema funcionando: criar, listar,
editar e excluir. Então, resumindo as principais funcionalidades: listagem de reservas, cadastro de
novas reservas, edição de reservas existentes e exclusão. Tudo isso com uma interface limpa, feita com
Thymeleaf e estilizada com Bootstrap, para que a navegação seja agradável e fácil de entender.

Em termos de visão geral da aplicação, nós construímos tudo em **Java com Spring Boot**, usando
**Thymeleaf** para renderizar as telas no servidor e **MySQL** como banco de dados para guardar as
reservas de forma permanente. Essa é a fotografia do projeto para qualquer pessoa que esteja nos
ouvindo. A partir de agora, eu vou entrar em duas partes mais específicas: primeiro a parte da
disciplina de Orientação a Objetos, mostrando como o código está organizado por dentro, e depois a
parte de Testes e Qualidade, mostrando como nós garantimos que o sistema funciona de forma confiável.

---

## Parte 2 — Linguagem de Programação Orientada a Objetos II

Agora eu queria abrir o sistema por dentro e mostrar, **código a código**, como ele está estruturado,
porque é aqui que ficam os conceitos de Java e de orientação a objetos que aprendemos na disciplina.
Eu vou trazer os trechos relevantes para a tela e ir comentando cada um deles.

O ponto de partida da aplicação é a classe `SistemaReservasApplication`. Ela é bem pequena, mas é
estratégica. Vou mostrar o código dela na tela:

```java
@SpringBootApplication
public class SistemaReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaReservasApplication.class, args);
    }
}
```

Reparem em duas coisas. A primeira é o método `main`, que é o ponto de entrada de qualquer programa
Java. A segunda, e mais importante, é a anotação `@SpringBootApplication`, ali em cima da classe. Na
prática, essa anotação é o que liga o motor do Spring Boot — ela ativa a configuração automática, a
varredura de componentes e levanta todo o servidor embutido. Ou seja, com pouquíssimo código a gente
sobe a aplicação inteira.

A primeira coisa que eu gostaria de destacar é que o sistema foi organizado em **camadas bem
separadas**, seguindo o que se costuma chamar de arquitetura em camadas. Nós temos quatro papéis
principais: a **entidade** (`model`), o **repositório** (`repository`), o **serviço** (`service`) e
o **controlador** (`controller`). Cada uma dessas camadas tem uma responsabilidade única e bem
definida, e isso não é por acaso — é a aplicação direta do princípio de responsabilidade única, em
que cada classe deve ter um motivo claro para existir. Essa separação deixa o código mais fácil de
entender, de manter e de testar.

Deixa eu começar pela base de tudo, que é a entidade `Reserva`. Essa é a classe que representa, em
objeto Java, aquilo que no banco de dados é uma linha de uma tabela. Vou mostrar o cabeçalho da classe
e os atributos:

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
```

A primeira coisa que eu quero destacar aqui é o **encapsulamento**: vejam que todos os atributos — o
`id`, o `nomeCliente`, a `dataReserva`, a `quantidadePessoas` e o `status` — são declarados como
`private`. Isso significa que ninguém de fora acessa esses dados diretamente. O acesso só acontece
através dos métodos `get` e `set`, como neste par de exemplo:

```java
public String getNomeCliente() {
    return nomeCliente;
}

public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}
```

Esses getters e setters são a porta de entrada e de saída controlada para cada atributo. Esse é
exatamente o princípio do encapsulamento que estudamos: proteger o estado interno do objeto e expor
apenas o que for necessário.

Voltando àquela lista de atributos, reparem também na **tipagem**, porque cada um tem um tipo bem
escolhido. O `id` é um `Long`; a data é um `LocalDate`, que é a classe moderna do Java para
representar datas sem hora; a quantidade de pessoas é um `int`, que é um tipo primitivo; e tanto o
nome quanto o status são `String`. Esse cuidado com os tipos é importante porque é o que garante que o
dado certo seja armazenado da forma certa, e o compilador nos ajuda a evitar erros já em tempo de
compilação.

E agora o ponto que torna essa classe especial do ponto de vista de Java moderno: as **anotações de
mapeamento objeto-relacional**, o famoso ORM. Olhem de novo para as três anotações no código: a classe
é marcada com `@Entity`, o que diz ao Hibernate que ela corresponde a uma tabela no banco. O atributo
`id` recebe `@Id`, indicando que ele é a chave primária, e recebe também
`@GeneratedValue(strategy = GenerationType.IDENTITY)`, que delega ao próprio MySQL a geração automática
e incremental desse identificador. Lembram do Passo 3 da demonstração, quando o ID apareceu sozinho
depois de salvar? É exatamente essa anotação a responsável por isso. O ponto importante aqui é que eu
não escrevo SQL para nada disso: eu trabalho com objetos Java, e o framework cuida da tradução entre o
mundo dos objetos e o mundo das tabelas.

Subindo uma camada, chegamos ao **repositório**, a interface `ReservaRepository`. E essa parte é uma
das mais elegantes do projeto. Vou mostrar o código inteiro, porque ele cabe em poucas linhas:

```java
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
```

É só isso. Repare que `ReservaRepository` é apenas uma **interface**, e ela não tem nenhuma
implementação escrita por nós — o corpo está literalmente vazio. Ela simplesmente **estende**
`JpaRepository`, informando dois tipos genéricos entre os sinais de menor e maior: a entidade
`Reserva` e o tipo da sua chave primária, que é `Long`. A partir desse momento, o Spring Data JPA gera
automaticamente, em tempo de execução, toda a implementação dos métodos de acesso ao banco — como
`save`, `findAll`, `findById` e `deleteById`. Aqui aparecem dois conceitos fortes que vimos na
disciplina: o uso de **generics**, com aqueles tipos parametrizados, e o uso de **herança de
interface**, em que a minha interface herda um contrato pronto e cheio de comportamento. É um exemplo
claro de como programar voltado para abstrações nos poupa muito código.

Na camada seguinte, temos o **serviço**, a classe `ReservaService`, anotada com `@Service`. Essa é a
camada que concentra a lógica de negócio e que serve de ponte entre o controlador e o repositório. Vou
mostrar primeiro o topo da classe e o construtor:

```java
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    // Injeta o repository para acessar o banco
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
```

É aqui que eu gostaria de chamar a atenção para a **injeção de dependência**, que é um conceito central
tanto em orientação a objetos quanto no Spring. Reparem que o `ReservaService` declara o
`ReservaRepository` como um atributo `private final`, e o recebe pelo **construtor**. Ele não cria o
repositório com `new` em lugar nenhum; em vez disso, ele apenas declara que precisa de um, e o próprio
Spring se encarrega de fornecer essa dependência pronta. Isso é o que chamamos de inversão de controle.
O ganho disso é enorme: as classes ficam desacopladas, e essa mesma característica é o que vai
permitir, mais para frente, que a gente substitua o repositório real por um objeto simulado durante os
testes.

Agora vamos aos métodos do serviço, que são curtos e diretos:

```java
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
```

Olhem como cada método da camada de serviço corresponde a uma das funcionalidades que eu demonstrei lá
no começo: `salvar` é o que executa quando clico em Salvar, `listarTodas` é o que monta a tabela,
`buscarPorId` é o que carrega os dados na edição, e `excluir` é o botão vermelho. E vale comentar o uso
de **coleções e de tipos genéricos** nas assinaturas. O método `listarTodas`, por exemplo, devolve uma
`List<Reserva>`, ou seja, uma lista tipada de reservas. E o método `buscarPorId` devolve um
`Optional<Reserva>`. Esse `Optional` é um detalhe que eu acho importante destacar, porque ele é a forma
moderna do Java de representar algo que **pode ou não existir**. Em vez de devolver `null` e correr o
risco do famoso `NullPointerException`, eu devolvo um `Optional`, que obriga quem recebe a tratar de
forma explícita o caso em que a reserva não foi encontrada. É uma escolha que demonstra preocupação com
robustez.

Por fim, no topo, está o **controlador**, a classe `ReservaController`, anotada com `@Controller`.
Essa é a camada que recebe as requisições do navegador e decide o que responder. Vou mostrar o começo
da classe, com o construtor e as duas primeiras rotas:

```java
@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Abre direto a tela principal (lista)
    @GetMapping("/")
    public String inicio() {
        return "redirect:/reservas";
    }

    // Lista todas as reservas
    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "lista-reservas";
    }
```

Reparem que ela também recebe o `ReservaService` por injeção de dependência no construtor, mantendo a
mesma filosofia de baixo acoplamento. E aqui aparecem as anotações de mapeamento web. Aquele
`@GetMapping("/")` é exatamente o que faz a aplicação, no Passo 1 da demonstração, redirecionar direto
para a lista. E o `@GetMapping("/reservas")` é o que monta a tabela: ele chama o serviço, coloca as
reservas no `Model` e devolve o nome da tela `lista-reservas`.

Vou mostrar agora as rotas que tratam o cadastro:

```java
// Abre o formulario para cadastrar nova reserva
@GetMapping("/reservas/nova")
public String novaReserva(Model model) {
    model.addAttribute("reserva", new Reserva());
    return "formulario-reserva";
}

// Salva nova reserva ou edicao
@PostMapping("/reservas/salvar")
public String salvarReserva(@ModelAttribute Reserva reserva) {
    reservaService.salvar(reserva);
    return "redirect:/reservas";
}
```

Aqui aparece a diferença entre os dois verbos HTTP: o `@GetMapping` responde às requisições de
visualização, como abrir o formulário vazio, e o `@PostMapping` trata o envio do formulário. O
`@ModelAttribute` é o que faz a mágica de pegar os campos que o usuário preencheu na tela e montar, de
forma automática, um objeto `Reserva` já preenchido. Depois de salvar, repare no `return
"redirect:/reservas"`: é literalmente isso que faz a tela voltar para a lista no Passo 3.

E eu queria fechar essa parte mostrando o trecho que, para mim, resume bem a maturidade do código: o
método de edição, junto com o de exclusão, que usam `@PathVariable`:

```java
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

// Exclui a reserva pela lista
@GetMapping("/reservas/excluir/{id}")
public String excluirReserva(@PathVariable Long id) {
    reservaService.excluir(id);
    return "redirect:/reservas";
}
```

Repare no `@PathVariable Long id`: ele captura o número que vem na própria URL, como em
`/reservas/editar/1`. E olhem o tratamento do método de edição: quando o usuário pede para editar, o
controlador chama o serviço, que devolve aquele `Optional`. E aí, em vez de um monte de `if` para
verificar nulo, eu uso programação funcional com `map` e `orElse`: se a reserva existe, eu a coloco no
modelo e abro o formulário já preenchido — que foi o que vimos no Passo 4; se ela não existe, eu
simplesmente redireciono de volta para a lista. Em poucas linhas, eu trato com elegância os dois
caminhos possíveis.

### O mapa completo de rotas

Antes de seguir, eu queria parar um pouco e mostrar, de forma organizada, **todas as rotas** que esse
controlador expõe, porque elas são a porta de entrada da aplicação inteira. São seis rotas, e cada uma
responde a um tipo específico de requisição HTTP:

| Verbo HTTP | Rota | Método Java | O que faz |
| --- | --- | --- | --- |
| `GET` | `/` | `inicio()` | Redireciona direto para a lista de reservas |
| `GET` | `/reservas` | `listarReservas()` | Monta a tabela com todas as reservas |
| `GET` | `/reservas/nova` | `novaReserva()` | Abre o formulário vazio para cadastro |
| `POST` | `/reservas/salvar` | `salvarReserva()` | Grava uma reserva nova ou editada |
| `GET` | `/reservas/editar/{id}` | `editarReserva()` | Abre o formulário já preenchido |
| `GET` | `/reservas/excluir/{id}` | `excluirReserva()` | Remove a reserva e volta para a lista |

Olhando essa tabela, dá para perceber uma decisão de projeto importante: eu uso o verbo `GET` para
tudo que é **navegação e leitura** — abrir a lista, abrir um formulário, disparar uma exclusão a partir
de um link — e reservo o verbo `POST` exclusivamente para o momento em que **dados são enviados** do
formulário para o servidor, que é o salvar. Essa separação entre `GET` e `POST` é um dos fundamentos do
protocolo HTTP, e o Spring deixa isso explícito justamente através das anotações `@GetMapping` e
`@PostMapping`.

### O que acontece, por dentro, quando uma requisição chega

Agora deixa eu detalhar o **caminho que uma requisição percorre**, porque é aqui que a coisa fica
interessante. Imaginem que o usuário clicou em "Nova Reserva". O navegador dispara uma requisição
`GET /reservas/nova` para o servidor. Quem recebe essa requisição primeiro não é o nosso código: é um
componente interno do Spring chamado `DispatcherServlet`, que funciona como um porteiro central. Ele
olha a URL e o verbo, consulta o mapeamento de rotas e descobre que `GET /reservas/nova` corresponde ao
método `novaReserva` do nosso `ReservaController`. Só então ele entrega o controle para o nosso código.

O nosso método executa, coloca um objeto `Reserva` vazio dentro do `Model` — que é basicamente uma
maleta de dados que viaja do controlador para a tela — e devolve uma `String`: `"formulario-reserva"`.
E aqui acontece outra etapa que costuma passar despercebida: essa `String` **não é** o HTML final. Ela
é apenas o **nome lógico** de uma tela. O Spring entrega esse nome a um componente chamado
`ViewResolver`, que sabe que `"formulario-reserva"` corresponde ao arquivo
`templates/formulario-reserva.html`. É o Thymeleaf que, então, pega esse template, junta com os dados
da maleta `Model` e gera o HTML de verdade, que finalmente volta para o navegador.

E tem um detalhe que eu acho elegante no fluxo de salvar. Reparem que, depois de gravar, eu não devolvo
uma tela: eu devolvo `"redirect:/reservas"`. Isso é o que se chama de padrão **Post-Redirect-Get**. Em
vez de responder o `POST` diretamente com uma página, eu mando o navegador fazer uma **nova requisição**
`GET` para a lista. O ganho prático disso é que, se o usuário apertar F5 para atualizar a página depois
de salvar, ele não corre o risco de reenviar o formulário e cadastrar a reserva duas vezes. É uma
pequena decisão que evita um bug clássico de aplicações web.

### O caminho até o banco de dados

E agora eu chego no que, para mim, amarra toda a apresentação: **o caminho que o dado percorre até o
banco de dados**. Vamos seguir uma única reserva, do clique até o MySQL. Quando o usuário envia o
formulário, a requisição `POST /reservas/salvar` chega ao **controller**. O controller não sabe nada
sobre banco de dados — a única coisa que ele faz é chamar `reservaService.salvar(reserva)`. O
**service**, por sua vez, também não escreve SQL nenhum: ele apenas repassa para
`reservaRepository.save(reserva)`. O **repository**, que é só aquela interface vazia estendendo
`JpaRepository`, é traduzido em tempo de execução pelo **Spring Data JPA**, que aciona o **Hibernate**.
É o Hibernate, o nosso ORM, que finalmente transforma o objeto `Reserva` em um comando SQL `INSERT` ou
`UPDATE` e o envia, através do **driver JDBC do MySQL**, para o banco. Resumindo a cadeia:

> **controller → service → repository → Spring Data JPA → Hibernate → driver JDBC → MySQL**

Cada seta dessa é uma fronteira de responsabilidade, e ninguém "fura" a camada de baixo. O controller
nunca fala direto com o banco; ele sempre passa pelo service e pelo repository. Isso é o que mantém o
código organizado e testável.

Mas falta uma peça: como é que a aplicação **sabe onde fica esse banco**? Essa configuração não está no
código Java, e sim em um arquivo de propriedades, o `application.properties`. Vou mostrar os trechos que
definem a conexão:

```properties
# Configuração de conexão com o banco MySQL local
spring.datasource.url=jdbc:mysql://localhost:3306/reservas_db
spring.datasource.username=root
spring.datasource.******   # senha definida no application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Atualiza as tabelas automaticamente com base nas entidades
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Reparem nas três informações essenciais. A `spring.datasource.url` é o **endereço** do banco: o
protocolo `jdbc:mysql`, o host `localhost`, a porta `3306` e o nome do banco `reservas_db`. Logo abaixo
vêm o usuário e a senha. E o `driver-class-name` é a peça que ensina o Java a "conversar" o dialeto do
MySQL. Há ainda dois ajustes que valem o comentário: o `ddl-auto=update`, que faz o Hibernate **criar e
atualizar as tabelas sozinho** a partir das nossas entidades — é por isso que eu nunca precisei escrever
um `CREATE TABLE` na mão; e o `show-sql=true`, que imprime no console o SQL gerado, o que é ótimo para
estudar e enxergar exatamente o comando que sai daquela cadeia toda que eu acabei de descrever.

E só para fechar essa ideia: como o projeto também roda em Docker, existe um segundo arquivo, o
`application-docker.properties`, que muda apenas o host do banco de `localhost` para `db` — que é o nome
do serviço do MySQL lá no `docker-compose`. É o mesmo código Java, apenas com um endereço de banco
diferente, o que mostra na prática como a configuração fica separada da lógica da aplicação.

Então, juntando tudo: a requisição entra pelo **controller**, que conversa com o **service**, que por
sua vez conversa com o **repository**, que — por meio do JPA e do Hibernate, configurados pelo
`application.properties` — persiste a **entity** no MySQL. É um fluxo limpo, em camadas, com
responsabilidades separadas, encapsulamento, injeção de dependência, generics, coleções e mapeamento
objeto-relacional. Todos esses conceitos que a gente estudou em Orientação a Objetos estão aplicados de
forma concreta aqui.

---

## Parte 3 — Testes e Qualidade de Software

Agora eu mudo de assunto e vou falar sobre a outra frente do trabalho, que é a de **Testes e
Qualidade**. E aqui eu organizei a apresentação seguindo as **oito fases** que estruturamos no nosso
documento de QA, o arquivo `testes.md`. A ideia dessas fases é mostrar uma evolução: a gente partiu de
um sistema que funcionava, mas não tinha nenhuma rede de proteção, e foi construindo, passo a passo,
um processo de qualidade completo.

**Fase 1 — Diagnóstico de Qualidade.** Tudo começou com um diagnóstico honesto da situação. A gente
olhou para o sistema e mapeou os quatro fluxos principais: listar, cadastrar, editar e excluir
reservas. A partir disso, identificamos os riscos mais relevantes: não havia testes automatizados, as
validações dependiam basicamente do front-end, não existia um pipeline de qualidade e não tínhamos
nenhum indicador para acompanhar a saúde do projeto. A conclusão dessa fase foi clara: o sistema
funcionava, mas precisava de uma base de qualidade para poder evoluir com segurança.

**Fase 2 — Estratégia de Testes.** Com o diagnóstico em mãos, definimos uma estratégia baseada na
**pirâmide de testes**, que é um conceito clássico de QA. Na base, muitos testes unitários, rápidos e
baratos; no meio, testes de integração para validar a conversa entre as camadas; e, no topo, poucos
testes de ponta a ponta, os chamados End-to-End, validando o fluxo crítico do usuário. Também
definimos critérios simples de entrada, aceite e saída para cada teste, e planejamos a regressão em
três momentos: a cada alteração de código, em consolidações periódicas e na validação final antes da
entrega.

**Fase 3 — Testes Manuais e Modelagem.** Antes de automatizar, aplicamos técnicas formais de
modelagem de testes, que a gente estudou na disciplina: particionamento de equivalência, análise de
valor limite, tabela de decisão e transição de estados. Essas técnicas serviram para desenhar bons
cenários e para padronizar a forma como a gente registra um defeito, sempre com severidade,
prioridade, passos de reprodução e evidências.

**Fase 4 — Automação de Testes.** Aqui está, para mim, o coração da parte prática, porque é onde a
estratégia vira código de verdade. A gente automatizou os testes em camadas complementares, e cada
uma valida uma coisa específica do sistema.

A primeira camada são os **testes unitários do serviço**, no arquivo `ReservaServiceTest`. Esses
testes usam o **JUnit 5** junto com o **Mockito**. E é exatamente aqui que aquela injeção de
dependência que eu expliquei na parte de orientação a objetos mostra o seu valor: como o serviço
recebe o repositório de fora, eu consigo injetar um **mock**, um repositório falso, com as anotações
`@Mock` e `@InjectMocks`. Com isso, eu testo a lógica do serviço de forma totalmente isolada do banco
de dados, verificando, por exemplo, se ao salvar uma reserva o método correto do repositório é
realmente chamado. [posso mostrar esse trecho no `trecho.md`]

A segunda camada são os **testes de integração web**, no arquivo `ReservaControllerWebTest`, que usam
a anotação `@WebMvcTest` e o `MockMvc`. Esses testes simulam requisições HTTP de verdade contra o
controlador e verificam o comportamento esperado: se a rota raiz redireciona para a lista, se a página
certa é renderizada, se o formulário salva e redireciona, e — algo que eu acho importante — se a
edição de um `id` que não existe redireciona corretamente em vez de quebrar. Ou seja, a gente valida
o caminho feliz e também o caminho de exceção.

A terceira camada são os **testes de persistência**, no arquivo `ReservaRepositoryTest`, com a
anotação `@DataJpaTest`. Esse teste sobe um banco em memória, o **H2**, salva uma reserva de verdade
e depois busca de volta para conferir se os dados foram gravados e recuperados corretamente. Isso nos
dá confiança de que o mapeamento objeto-relacional está funcionando como esperado.

E, no topo, a quarta camada é o **teste End-to-End com Playwright**, no arquivo
`reserva-fluxo-critico.spec.js`. Esse teste abre um navegador de verdade, acessa o sistema, clica em
"Nova Reserva", preenche todos os campos, salva e confere se a reserva realmente apareceu na lista. É
o teste que mais se aproxima da experiência real do usuário, validando o fluxo crítico de ponta a
ponta. [neste momento, posso mostrar o teste E2E rodando, se houver tempo]

**Fase 5 — Uso de IA nos Testes.** Nessa fase, a gente usou inteligência artificial como ferramenta
de apoio, principalmente para sugerir massa de dados e cenários de exceção. Mas fizemos isso de forma
consciente e controlada: todo cenário gerado foi revisado manualmente, porque a IA pode inventar
regras que não existem, pode não conhecer todo o contexto do domínio e pode gerar cenários
redundantes. Então a IA entrou como apoio, e nunca como substituta da análise de quem está fazendo a
qualidade.

**Fase 6 — Pipeline de Qualidade.** Para que nada disso dependesse de a gente lembrar de rodar os
testes na mão, montamos uma esteira de **CI/CD** com o GitHub Actions, no arquivo `ci-qa.yml`. A cada
push ou pull request, o pipeline compila o projeto, roda toda a bateria de testes Java e depois sobe a
aplicação para executar os testes End-to-End do Playwright. Ao final, ele ainda publica os relatórios
de teste e o relatório de cobertura do **JaCoCo** como artefatos. Isso garante rastreabilidade e faz
com que a qualidade seja verificada de forma automática e contínua. [posso apontar o trecho do
pipeline no `trecho.md`]

**Fase 7 — Testes Não Funcionais.** Além de verificar se o sistema faz o que deveria, a gente também
olhou para *como* ele se comporta. Nessa fase, planejamos uma validação inicial de desempenho, com
uma carga moderada no endpoint crítico, com o objetivo de estabelecer uma referência de latência e de
taxa de erro para acompanhar a evolução no futuro. E, como extensão, deixamos prevista uma avaliação
de segurança em uma etapa posterior.

**Fase 8 — Métricas e Governança.** Por fim, para fechar o ciclo, definimos quatro indicadores para
acompanhar a qualidade de forma contínua: cobertura de testes, densidade de defeitos, taxa de falhas
em produção e o MTTR, que é o tempo médio para correção. E, com base nesses indicadores, estabelecemos
uma política objetiva e simples: se o build falha, ele bloqueia a integração; e se existe um defeito
crítico, ele bloqueia a liberação.

Juntando essas oito fases, a mensagem que eu quero passar é que a qualidade, no nosso projeto, não foi
tratada como algo pontual no final, mas como um **processo contínuo**. A gente saiu de um sistema sem
nenhuma rede de proteção e chegou a um conjunto equilibrado de testes — unitários, de integração, de
persistência e de ponta a ponta — todos rodando automaticamente em um pipeline. E é justamente essa
combinação que dá confiabilidade ao sistema, porque cada camada protege uma parte diferente, e juntas
elas reduzem muito o risco de uma alteração quebrar algo sem ninguém perceber.

---

## Encerramento

Para concluir, eu queria amarrar as três partes. Na visão geral, a gente viu um sistema de reservas
simples e direto, que resolve um problema real de organização. Na parte de Orientação a Objetos, a
gente abriu o código e viu uma arquitetura em camadas bem definida, com encapsulamento, injeção de
dependência, generics, mapeamento objeto-relacional e uso de recursos modernos do Java, como o
`Optional`. E, na parte de Qualidade, a gente percorreu as oito fases que transformaram esse sistema
em algo confiável e testado de forma automática.

No fim, o que esse projeto mostra é que mesmo uma aplicação enxuta pode ser construída com bom design
de código e com um processo sério de qualidade. Eu agradeço a atenção de todos e fico à disposição
para as perguntas. Caso seja necessário, todos os trechos de código que eu mencionei estão reunidos no
arquivo `trecho.md`, e posso abrir qualquer um deles para detalhar.
