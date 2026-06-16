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
porque é aqui que ficam os conceitos de Java e de orientação a objetos da disciplina. Os trechos que eu
vou citar estão todos reunidos no arquivo `trecho.md`, e eu vou abrindo cada um conforme for falando.

> **[Abrir `SistemaReservasApplication.java`, em `src/main/java/com/exemplo/reservas/` — trecho 2.1 do `trecho.md`]**

O ponto de partida é a classe `SistemaReservasApplication`. Ela é pequena, mas estratégica. Repare em
duas coisas: o método `main`, que é o ponto de entrada de qualquer programa Java, e a anotação
`@SpringBootApplication`. É ela que liga o motor do Spring Boot — ativa a configuração automática, a
varredura de componentes e levanta o servidor embutido. Com pouquíssimo código, a gente sobe a
aplicação inteira.

A grande decisão de design é que o sistema está organizado em **camadas bem separadas**: a **entidade**
(`model`), o **repositório** (`repository`), o **serviço** (`service`) e o **controlador**
(`controller`). Cada uma tem uma responsabilidade única — é o princípio da responsabilidade única
aplicado na prática, e é o que deixa o código fácil de entender, manter e testar.

> **[Abrir `Reserva.java`, em `src/main/java/com/exemplo/reservas/model/` — trecho 2.2 do `trecho.md`]**

Começo pela base de tudo, a entidade `Reserva`, que representa em objeto Java aquilo que no banco é uma
linha de uma tabela. Três pontos merecem destaque aqui. Primeiro, o **encapsulamento**: todos os
atributos são `private` e só são acessados pelos métodos `get` e `set` — o estado interno fica
protegido. Segundo, a **tipagem**: cada atributo tem o tipo certo, como o `LocalDate` para a data e o
`int` para a quantidade, o que deixa o compilador nos ajudar a evitar erros. E terceiro, o que torna a
classe especial: as **anotações de ORM**. O `@Entity` diz ao Hibernate que a classe é uma tabela, e o
par `@Id` com `@GeneratedValue(IDENTITY)` delega ao MySQL a geração automática do identificador —
lembram do ID que apareceu sozinho no Passo 3? É essa anotação. O importante é que eu não escrevo SQL:
trabalho com objetos Java, e o framework cuida da tradução.

> **[Abrir `ReservaRepository.java`, em `src/main/java/com/exemplo/reservas/repository/` — trecho 2.3 do `trecho.md`]**

Subindo uma camada, chego ao **repositório**, e essa é uma das partes mais elegantes do projeto. Repare
que `ReservaRepository` é só uma **interface** de corpo vazio: ela apenas **estende** `JpaRepository`,
informando dois tipos genéricos, a entidade `Reserva` e o tipo da chave, `Long`. A partir daí, o Spring
Data JPA gera sozinho toda a implementação — `save`, `findAll`, `findById`, `deleteById`. Em poucas
linhas aparecem dois conceitos fortes da disciplina: **generics** e **herança de interface**, herdando
um contrato pronto e cheio de comportamento.

> **[Abrir `ReservaService.java`, em `src/main/java/com/exemplo/reservas/service/` — trecho 2.4 do `trecho.md`]**

Na camada seguinte está o **serviço**, anotado com `@Service`, que concentra a lógica e faz a ponte
entre o controlador e o repositório. O destaque aqui é a **injeção de dependência**: o `ReservaService`
declara o `ReservaRepository` como `private final` e o recebe pelo **construtor** — ele nunca faz `new`
do repositório, apenas declara que precisa de um, e o Spring o entrega pronto. Isso é inversão de
controle, e é o que deixa as classes desacopladas — exatamente o que vai permitir, nos testes, trocar o
repositório real por um simulado. Reparem ainda nos métodos: `listarTodas` devolve uma `List<Reserva>`,
uma **coleção tipada**, e `buscarPorId` devolve um `Optional<Reserva>`. Esse `Optional` é a forma
moderna do Java de representar algo que **pode ou não existir** — em vez de devolver `null` e arriscar
um `NullPointerException`, eu obrigo quem chama a tratar o caso de não encontrar.

> **[Abrir `ReservaController.java`, em `src/main/java/com/exemplo/reservas/controller/` — trecho 2.5 do `trecho.md`]**

E no topo está o **controlador**, anotado com `@Controller`, que recebe as requisições do navegador e
decide o que responder. Ele também recebe o `ReservaService` por injeção de dependência, mantendo o
baixo acoplamento. Repare nas anotações de mapeamento web: o `@GetMapping("/")` é o que redireciona
direto para a lista no Passo 1; o `@GetMapping` trata as requisições de visualização, e o `@PostMapping`
trata o envio do formulário, com o `@ModelAttribute` montando sozinho o objeto `Reserva` a partir dos
campos preenchidos. E o trecho que melhor resume a maturidade do código é o método de edição: o
`@PathVariable Long id` captura o número da própria URL, como em `/reservas/editar/1`, e em vez de um
monte de `if` para verificar nulo, eu uso `map` e `orElse` sobre o `Optional` — se a reserva existe,
abro o formulário já preenchido; se não existe, redireciono de volta para a lista. Dois caminhos
tratados com elegância em poucas linhas.

### O mapa completo de rotas

Antes de seguir, vale ver de forma organizada **todas as rotas** que esse controlador expõe, porque
elas são a porta de entrada da aplicação. São seis, cada uma respondendo a um tipo de requisição HTTP:

| Verbo HTTP | Rota | Método Java | O que faz |
| --- | --- | --- | --- |
| `GET` | `/` | `inicio()` | Redireciona direto para a lista de reservas |
| `GET` | `/reservas` | `listarReservas()` | Monta a tabela com todas as reservas |
| `GET` | `/reservas/nova` | `novaReserva()` | Abre o formulário vazio para cadastro |
| `POST` | `/reservas/salvar` | `salvarReserva()` | Grava uma reserva nova ou editada |
| `GET` | `/reservas/editar/{id}` | `editarReserva()` | Abre o formulário já preenchido |
| `GET` | `/reservas/excluir/{id}` | `excluirReserva()` | Remove a reserva e volta para a lista |

A decisão de projeto fica clara nessa tabela: uso `GET` para tudo que é **navegação e leitura** e
reservo o `POST` para o momento em que **dados são enviados** do formulário, que é o salvar. Essa
separação entre `GET` e `POST` é um fundamento do HTTP, e o Spring a deixa explícita nas anotações
`@GetMapping` e `@PostMapping`.

### O que acontece, por dentro, quando uma requisição chega

Deixa eu detalhar o **caminho que uma requisição percorre**. Imaginem que o usuário clicou em "Nova
Reserva": o navegador dispara um `GET /reservas/nova`. Quem recebe primeiro não é o nosso código, e sim
o `DispatcherServlet`, um porteiro central do Spring, que olha a URL e o verbo, consulta o mapeamento e
descobre que aquela rota corresponde ao método `novaReserva`. Só então ele entrega o controle ao nosso
controlador.

Nosso método coloca um `Reserva` vazio no `Model` — a maleta de dados que viaja até a tela — e devolve a
`String` `"formulario-reserva"`. E aqui há uma etapa que costuma passar despercebida: essa `String`
**não é** o HTML, é só o **nome lógico** da tela. O `ViewResolver` traduz esse nome para o arquivo
`templates/formulario-reserva.html`, e é o Thymeleaf que junta o template com os dados do `Model` e gera
o HTML que volta ao navegador.

E tem um detalhe elegante no salvar: depois de gravar, eu não devolvo uma tela, devolvo
`"redirect:/reservas"`. É o padrão **Post-Redirect-Get** — em vez de responder o `POST` com uma página,
mando o navegador fazer um novo `GET` para a lista. Assim, se o usuário apertar F5, ele não reenvia o
formulário nem cadastra a reserva em duplicidade. Uma pequena decisão que evita um bug clássico da web.

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
código Java, e sim no arquivo `application.properties`.

> **[Abrir `application.properties`, em `src/main/resources/` — trecho 2.6 do `trecho.md`]**

Reparem nas informações essenciais. A `spring.datasource.url` é o **endereço** do banco — protocolo
`jdbc:mysql`, host `localhost`, porta `3306` e o nome `reservas_db` —, seguida do usuário e da senha; e
o `driver-class-name` é a peça que ensina o Java a falar o dialeto do MySQL. Dois ajustes merecem
destaque: o `ddl-auto=update`, que faz o Hibernate **criar e atualizar as tabelas sozinho** a partir das
entidades — por isso eu nunca escrevi um `CREATE TABLE` na mão —, e o `show-sql=true`, que imprime no
console o SQL gerado. E só para fechar: como o projeto também roda em Docker, o
`application-docker.properties` muda apenas o host de `localhost` para `db`, o nome do serviço do MySQL
no `docker-compose` — é o mesmo código Java, só com um endereço de banco diferente.

Então, juntando tudo: a requisição entra pelo **controller**, conversa com o **service**, que conversa
com o **repository**, que — via JPA e Hibernate, configurados pelo `application.properties` — persiste a
**entity** no MySQL. É um fluxo limpo, em camadas, com responsabilidades separadas, encapsulamento,
injeção de dependência, generics, coleções e mapeamento objeto-relacional. Todos esses conceitos que
estudamos em Orientação a Objetos estão aplicados de forma concreta aqui.

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
realmente chamado.

> **[Abrir `ReservaServiceTest.java`, em `src/test/java/com/exemplo/reservas/service/` — trecho 3.1 do `trecho.md`]**

A segunda camada são os **testes de integração web**, no arquivo `ReservaControllerWebTest`, que usam
a anotação `@WebMvcTest` e o `MockMvc`. Esses testes simulam requisições HTTP de verdade contra o
controlador e verificam o comportamento esperado: se a rota raiz redireciona para a lista, se a página
certa é renderizada, se o formulário salva e redireciona, e — algo que eu acho importante — se a
edição de um `id` que não existe redireciona corretamente em vez de quebrar. Ou seja, a gente valida
o caminho feliz e também o caminho de exceção.

> **[Abrir `ReservaControllerWebTest.java`, em `src/test/java/com/exemplo/reservas/controller/` — trecho 3.2 do `trecho.md`]**

A terceira camada são os **testes de persistência**, no arquivo `ReservaRepositoryTest`, com a
anotação `@DataJpaTest`. Esse teste sobe um banco em memória, o **H2**, salva uma reserva de verdade
e depois busca de volta para conferir se os dados foram gravados e recuperados corretamente. Isso nos
dá confiança de que o mapeamento objeto-relacional está funcionando como esperado.

> **[Abrir `ReservaRepositoryTest.java`, em `src/test/java/com/exemplo/reservas/repository/` — trecho 3.3 do `trecho.md`]**

E, no topo, a quarta camada é o **teste End-to-End com Playwright**, no arquivo
`reserva-fluxo-critico.spec.js`. Esse teste abre um navegador de verdade, acessa o sistema, clica em
"Nova Reserva", preenche todos os campos, salva e confere se a reserva realmente apareceu na lista. É
o teste que mais se aproxima da experiência real do usuário, validando o fluxo crítico de ponta a
ponta.

> **[Abrir `reserva-fluxo-critico.spec.js`, em `e2e/playwright/tests/` — trecho 3.4 do `trecho.md`; se houver tempo, mostrar o teste E2E rodando]**

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
com que a qualidade seja verificada de forma automática e contínua.

> **[Abrir `ci-qa.yml`, em `.github/workflows/` — trecho 3.5 do `trecho.md`]**

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
