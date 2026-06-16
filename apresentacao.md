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

Agora eu queria abrir o sistema por dentro e mostrar como ele está estruturado, porque é aqui que ficam
os conceitos de Java e de orientação a objetos da disciplina. Os trechos que eu vou citar estão reunidos
no arquivo `trecho.md`. A ideia central é que o sistema está organizado em **camadas bem separadas**: a
**entidade** (`model`), o **repositório** (`repository`), o **serviço** (`service`) e o **controlador**
(`controller`). Cada camada tem uma responsabilidade única, e é isso que deixa o código fácil de
entender, manter e testar.

Começando pela base, a entidade `Reserva` é o objeto Java que representa uma linha da tabela no banco.
Nela aparecem três conceitos importantes: o **encapsulamento**, com os atributos `private` acessados só
por `get` e `set`; a **tipagem forte**, com `LocalDate` para a data e `int` para a quantidade; e as
**anotações de ORM**, em que o `@Entity` diz ao Hibernate que a classe é uma tabela e o `@Id` com
`@GeneratedValue` delega ao MySQL a geração automática do ID — aquele número que apareceu sozinho no
Passo 3. O ponto principal é que eu não escrevo SQL: trabalho com objetos, e o framework traduz.

Subindo uma camada, o `ReservaRepository` é só uma **interface vazia** que **estende** `JpaRepository`,
informando a entidade `Reserva` e o tipo da chave, `Long`. A partir disso, o Spring Data JPA gera sozinho
todos os métodos de acesso ao banco, como `save`, `findAll` e `deleteById`. Aqui aparecem dois conceitos
da disciplina: **generics** e **herança de interface**. Em cima dele vem o `ReservaService`, anotado com
`@Service`, que concentra a lógica e usa **injeção de dependência**: ele recebe o repositório pelo
construtor em vez de criar um com `new`, e é justamente isso que vai permitir, nos testes, trocar o
repositório real por um simulado. Reparem ainda que ele devolve uma `List<Reserva>` na listagem e um
`Optional<Reserva>` na busca — esse `Optional` é a forma moderna de representar algo que **pode ou não
existir**, evitando o velho `NullPointerException`.

No topo está o `ReservaController`, anotado com `@Controller`, que recebe as requisições do navegador e
decide o que responder. E é aqui que entram as **rotas**, que são a porta de entrada da aplicação. Cada
rota é um método marcado com uma anotação que indica o verbo HTTP. Deixa eu explicar uma a uma o que faz:

- O **`GET` em `/`** é a porta da frente: ele simplesmente redireciona o usuário direto para a lista de
  reservas, que é o que aconteceu no Passo 1.
- O **`GET` em `/reservas`** é o que monta a tela de listagem: ele pede todas as reservas ao serviço e
  entrega para a tabela ser renderizada.
- O **`GET` em `/reservas/nova`** abre o formulário vazio para um cadastro novo.
- O **`POST` em `/reservas/salvar`** é o único que **recebe dados**: ele pega os campos preenchidos no
  formulário, montados automaticamente no objeto `Reserva` pelo `@ModelAttribute`, e manda gravar. Serve
  tanto para criar quanto para editar.
- O **`GET` em `/reservas/editar/{id}`** abre o formulário já preenchido. Repare no `{id}`: é o
  `@PathVariable`, que captura o número da própria URL, como em `/reservas/editar/1`.
- E o **`GET` em `/reservas/excluir/{id}`** remove a reserva daquele ID e volta para a lista.

A decisão de projeto fica clara: uso `GET` para tudo que é **navegação e leitura**, e reservo o `POST`
para o momento em que **dados são enviados**. Essa separação é um fundamento do HTTP, e o Spring a deixa
explícita nas anotações `@GetMapping` e `@PostMapping`. Um detalhe elegante: no salvar, depois de gravar,
eu não devolvo uma tela, devolvo um `redirect` para a lista. É o padrão **Post-Redirect-Get**, que evita
que o usuário cadastre a reserva em duplicidade se apertar F5.

Para fechar, vale ver **o caminho que o dado percorre até o banco**. Quando o formulário é enviado, a
requisição chega ao **controller**, que não sabe nada de banco e só chama o **service**; o service também
não escreve SQL e repassa para o **repository**; e o repository, via **Spring Data JPA** e **Hibernate**,
transforma o objeto `Reserva` em um comando SQL e o envia ao **MySQL** pelo driver JDBC. A cadeia é:

> **controller → service → repository → Spring Data JPA → Hibernate → driver JDBC → MySQL**

Ninguém "fura" a camada de baixo, e é isso que mantém o código organizado e testável. A única coisa que
fica fora do Java é **onde** está o banco: isso vem do arquivo `application.properties`, que guarda o
endereço, o usuário e a senha do MySQL, além do `ddl-auto=update`, que faz o Hibernate criar as tabelas
sozinho — por isso eu nunca escrevi um `CREATE TABLE` na mão. Juntando tudo, temos um fluxo em camadas,
com encapsulamento, injeção de dependência, generics, coleções e mapeamento objeto-relacional — todos os
conceitos de Orientação a Objetos aplicados de forma concreta.

---

## Parte 3 — Testes e Qualidade de Software

Agora eu mudo de assunto e vou falar sobre a outra frente do trabalho, que é a de **Testes e
Qualidade**. Organizei essa parte seguindo as **oito fases** do nosso documento de QA, o arquivo
`testes.md`. A ideia é mostrar uma evolução: a gente partiu de um sistema que funcionava, mas sem nenhuma
rede de proteção, e foi construindo, passo a passo, um processo de qualidade completo.

**Fase 1 — Diagnóstico de Qualidade.** Começamos com um diagnóstico honesto: mapeamos os quatro fluxos
principais — listar, cadastrar, editar e excluir — e identificamos os riscos. Não havia testes
automatizados, as validações dependiam do front-end, não existia pipeline e não tínhamos indicadores. A
conclusão foi clara: o sistema funcionava, mas precisava de uma base de qualidade para evoluir com
segurança.

**Fase 2 — Estratégia de Testes.** Definimos uma estratégia baseada na **pirâmide de testes**: na base,
muitos testes unitários, rápidos e baratos; no meio, testes de integração entre as camadas; e no topo,
poucos testes de ponta a ponta, os End-to-End, validando o fluxo crítico. Também definimos critérios de
entrada, aceite e saída, e planejamos a regressão a cada alteração, em consolidações periódicas e na
validação final.

**Fase 3 — Testes Manuais e Modelagem.** Antes de automatizar, aplicamos técnicas formais que estudamos
na disciplina: particionamento de equivalência, análise de valor limite, tabela de decisão e transição
de estados. Elas serviram para desenhar bons cenários e para padronizar o registro de defeitos, sempre
com severidade, prioridade, passos de reprodução e evidências.

**Fase 4 — Automação de Testes.** Esse é o coração da parte prática, onde a estratégia vira código. A
gente automatizou em quatro camadas complementares:

- Os **testes unitários do serviço**, em `ReservaServiceTest`, com **JUnit 5** e **Mockito**. É aqui que
  a injeção de dependência mostra o seu valor: como o serviço recebe o repositório de fora, eu injeto um
  **mock** com `@Mock` e `@InjectMocks` e testo a lógica isolada do banco.
- Os **testes de integração web**, em `ReservaControllerWebTest`, com `@WebMvcTest` e `MockMvc`. Eles
  simulam requisições HTTP contra o controlador e verificam o caminho feliz e também o de exceção, como
  editar um `id` que não existe sem quebrar.
- Os **testes de persistência**, em `ReservaRepositoryTest`, com `@DataJpaTest`. Sobem um banco em
  memória, o **H2**, salvam uma reserva e a buscam de volta, confirmando o mapeamento objeto-relacional.
- E o **teste End-to-End com Playwright**, em `reserva-fluxo-critico.spec.js`, que abre um navegador de
  verdade, cadastra uma reserva e confere se ela apareceu na lista — o teste mais próximo do usuário real.

> **[Trechos 3.1 a 3.4 do `trecho.md`; se houver tempo, mostrar o teste E2E rodando]**

**Fase 5 — Uso de IA nos Testes.** Usamos inteligência artificial como ferramenta de apoio, sobretudo
para sugerir massa de dados e cenários de exceção. Mas de forma controlada: todo cenário gerado foi
revisado manualmente, porque a IA pode inventar regras, desconhecer o contexto e gerar redundância.
Ela entrou como apoio, nunca como substituta da análise de quem faz a qualidade.

**Fase 6 — Pipeline de Qualidade.** Para não depender de rodar os testes na mão, montamos uma esteira de
**CI/CD** com o GitHub Actions, no arquivo `ci-qa.yml`. A cada push ou pull request, o pipeline compila o
projeto, roda os testes Java, sobe a aplicação para os testes End-to-End do Playwright e publica os
relatórios de teste e de cobertura do **JaCoCo** como artefatos, garantindo verificação automática e
contínua.

> **[Trecho 3.5 do `trecho.md`]**

**Fase 7 — Testes Não Funcionais.** Além de verificar se o sistema faz o que deveria, olhamos para
*como* ele se comporta. Planejamos uma validação inicial de desempenho, com carga moderada no endpoint
crítico, para estabelecer uma referência de latência e de taxa de erro, e deixamos prevista uma
avaliação de segurança em etapa posterior.

**Fase 8 — Métricas e Governança.** Por fim, definimos quatro indicadores para acompanhar a qualidade de
forma contínua: cobertura de testes, densidade de defeitos, taxa de falhas em produção e o MTTR, o tempo
médio para correção. E estabelecemos uma política simples: se o build falha, ele bloqueia a integração; e
se há um defeito crítico, ele bloqueia a liberação.

Juntando as oito fases, a mensagem é que a qualidade, no nosso projeto, não foi tratada como algo pontual
no final, mas como um **processo contínuo**. A gente saiu de um sistema sem rede de proteção e chegou a um
conjunto equilibrado de testes — unitários, de integração, de persistência e de ponta a ponta — rodando
automaticamente no pipeline. É essa combinação que dá confiabilidade ao sistema.

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
