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

[neste momento, posso mostrar a tela de listagem e, em seguida, o formulário de cadastro]

Então, resumindo as principais funcionalidades: listagem de reservas, cadastro de novas reservas,
edição de reservas existentes e exclusão. Tudo isso com uma interface limpa, feita com Thymeleaf e
estilizada com Bootstrap, para que a navegação seja agradável e fácil de entender.

Em termos de visão geral da aplicação, nós construímos tudo em **Java com Spring Boot**, usando
**Thymeleaf** para renderizar as telas no servidor e **MySQL** como banco de dados para guardar as
reservas de forma permanente. Essa é a fotografia do projeto para qualquer pessoa que esteja nos
ouvindo. A partir de agora, eu vou entrar em duas partes mais específicas: primeiro a parte da
disciplina de Orientação a Objetos, mostrando como o código está organizado por dentro, e depois a
parte de Testes e Qualidade, mostrando como nós garantimos que o sistema funciona de forma confiável.

---

## Parte 2 — Linguagem de Programação Orientada a Objetos II

Agora eu queria abrir o sistema por dentro e mostrar como ele está estruturado, porque é aqui que
ficam os conceitos de Java e de orientação a objetos que aprendemos na disciplina.

O ponto de partida da aplicação é a classe `SistemaReservasApplication`. Ela é bem pequena, mas é
estratégica: é nela que está o método `main`, e é ela que carrega a anotação `@SpringBootApplication`.
Na prática, essa anotação é o que liga o motor do Spring Boot — ela ativa a configuração automática,
a varredura de componentes e levanta todo o servidor embutido. Ou seja, com pouquíssimo código a
gente sobe a aplicação inteira. [aqui, posso apontar o trecho correspondente no `trecho.md`]

A primeira coisa que eu gostaria de destacar é que o sistema foi organizado em **camadas bem
separadas**, seguindo o que se costuma chamar de arquitetura em camadas. Nós temos quatro papéis
principais: a **entidade** (`model`), o **repositório** (`repository`), o **serviço** (`service`) e
o **controlador** (`controller`). Cada uma dessas camadas tem uma responsabilidade única e bem
definida, e isso não é por acaso — é a aplicação direta do princípio de responsabilidade única, em
que cada classe deve ter um motivo claro para existir. Essa separação deixa o código mais fácil de
entender, de manter e de testar.

Deixa eu começar pela base de tudo, que é a entidade `Reserva`. Essa é a classe que representa, em
objeto Java, aquilo que no banco de dados é uma linha de uma tabela. É aqui que aparece com força o
conceito de **encapsulamento**: todos os atributos — o `id`, o `nomeCliente`, a `dataReserva`, a
`quantidadePessoas` e o `status` — são declarados como `private`. Isso significa que ninguém de fora
acessa esses dados diretamente. O acesso só acontece através dos métodos `get` e `set`, os getters e
setters, que são a porta de entrada e de saída controlada para cada atributo. Esse é exatamente o
princípio do encapsulamento que estudamos: proteger o estado interno do objeto e expor apenas o que
for necessário.

Sobre a **tipagem**, vale reparar que cada atributo tem um tipo bem escolhido. O `id` é um `Long`, a
data é um `LocalDate`, que é a classe moderna do Java para representar datas sem hora, a quantidade
de pessoas é um `int`, que é um tipo primitivo, e tanto o nome quanto o status são `String`. Esse
cuidado com os tipos é importante porque é o que garante que o dado certo seja armazenado da forma
certa, e o compilador nos ajuda a evitar erros já em tempo de compilação.

O que torna essa classe especial, do ponto de vista de Java moderno, é o uso de **anotações de
mapeamento objeto-relacional**, o famoso ORM. A classe é marcada com `@Entity`, o que diz ao
Hibernate que ela corresponde a uma tabela no banco. O atributo `id` recebe `@Id`, indicando que ele
é a chave primária, e recebe também `@GeneratedValue` com a estratégia `IDENTITY`, que delega ao
próprio MySQL a geração automática e incremental desse identificador. O ponto importante aqui é que
eu não escrevo SQL para isso: eu trabalho com objetos Java, e o framework cuida da tradução entre o
mundo dos objetos e o mundo das tabelas. [posso mostrar o trecho da entidade no `trecho.md`]

Subindo uma camada, chegamos ao **repositório**, a interface `ReservaRepository`. E essa parte é uma
das mais elegantes do projeto, porque mostra bem o poder da abstração em Java. Repare que
`ReservaRepository` é apenas uma **interface**, e ela não tem nenhuma implementação escrita por nós.
Ela simplesmente **estende** `JpaRepository`, informando dois tipos genéricos: a entidade `Reserva` e
o tipo da sua chave primária, que é `Long`. A partir desse momento, o Spring Data JPA gera
automaticamente, em tempo de execução, toda a implementação dos métodos de acesso ao banco — como
`save`, `findAll`, `findById` e `deleteById`. Aqui aparecem dois conceitos fortes que vimos na
disciplina: o uso de **generics**, com aqueles tipos parametrizados entre os sinais de menor e maior,
e o uso de **herança de interface**, em que a minha interface herda um contrato pronto e cheio de
comportamento. É um exemplo claro de como programar voltado para abstrações nos poupa muito código.

Na camada seguinte, temos o **serviço**, a classe `ReservaService`, anotada com `@Service`. Essa é a
camada que concentra a lógica de negócio e que serve de ponte entre o controlador e o repositório. E
é aqui que eu gostaria de chamar a atenção para a **injeção de dependência**, que é um conceito
central tanto em orientação a objetos quanto no Spring. Repare que o `ReservaService` declara o
`ReservaRepository` como um atributo `private final`, e o recebe pelo **construtor**. Ele não cria o
repositório com `new`; em vez disso, ele apenas declara que precisa de um, e o próprio Spring se
encarrega de fornecer essa dependência pronta. Isso é o que chamamos de inversão de controle. O ganho
disso é enorme: as classes ficam desacopladas, e essa mesma característica é o que vai permitir, mais
para frente, que a gente substitua o repositório real por um objeto simulado durante os testes.

Ainda no serviço, vale comentar o uso de **coleções e de tipos genéricos** nas assinaturas dos
métodos. O método `listarTodas`, por exemplo, devolve uma `List<Reserva>`, ou seja, uma lista
tipada de reservas. E o método `buscarPorId` devolve um `Optional<Reserva>`. Esse `Optional` é um
detalhe que eu acho importante destacar, porque ele é a forma moderna do Java de representar algo que
**pode ou não existir**. Em vez de devolver `null` e correr o risco do famoso `NullPointerException`,
eu devolvo um `Optional`, que obriga quem recebe a tratar de forma explícita o caso em que a reserva
não foi encontrada. É uma escolha que demonstra preocupação com robustez.

Por fim, no topo, está o **controlador**, a classe `ReservaController`, anotada com `@Controller`.
Essa é a camada que recebe as requisições do navegador e decide o que responder. Ela também recebe o
`ReservaService` por injeção de dependência no construtor, mantendo a mesma filosofia de baixo
acoplamento. Aqui aparecem as anotações de mapeamento web: o `@GetMapping`, que responde às
requisições de visualização, e o `@PostMapping`, que trata o envio do formulário. Cada método está
associado a uma rota: a raiz redireciona para a lista, `/reservas` mostra a listagem, `/reservas/nova`
abre o formulário, `/reservas/salvar` persiste a reserva, e há ainda as rotas de editar e excluir,
que usam `@PathVariable` para capturar o `id` que vem na própria URL.

E eu queria fechar essa parte mostrando um trecho que, para mim, resume bem a maturidade do código: é
o método de edição. Quando o usuário pede para editar uma reserva, o controlador chama o serviço, que
devolve aquele `Optional`. E aí, em vez de um monte de `if` para verificar nulo, eu uso programação
funcional com `map` e `orElse`: se a reserva existe, eu a coloco no modelo e abro o formulário; se ela
não existe, eu simplesmente redireciono de volta para a lista. Em poucas linhas, eu trato com elegância
os dois caminhos possíveis. [aqui, posso apontar esse trecho no `trecho.md`]

Então, juntando tudo: a requisição entra pelo **controller**, que conversa com o **service**, que por
sua vez conversa com o **repository**, que persiste a **entity** no banco. É um fluxo limpo, em
camadas, com responsabilidades separadas, encapsulamento, injeção de dependência, generics, coleções
e mapeamento objeto-relacional. Todos esses conceitos que a gente estudou em Orientação a Objetos
estão aplicados de forma concreta aqui.

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
