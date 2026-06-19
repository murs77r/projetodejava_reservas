# Roteiro de Apresentacao - Testes do Projeto

Tempo total estimado: 11 a 13 minutos.

## Introducao (~30 segundos)
- **O que falar:** Bom dia/boa noite. Nesta parte da apresentacao, eu vou mostrar como a qualidade do Sistema de Reservas foi estruturada por meio de uma estrategia de testes. O objetivo nao foi mudar a regra de negocio principal do sistema, mas criar uma base de validacao para reduzir regressao, detectar falhas mais cedo e dar mais confianca para evoluir o projeto. Eu vou seguir a ordem das oito etapas documentadas no trabalho: diagnostico, estrategia, modelagem manual, automacao, IA, pipeline, testes nao funcionais e metricas de governanca.

---

## Topico 1: Diagnostico de Qualidade (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `src/main/java/com/exemplo/reservas/controller/ReservaController.java`. Destaque o bloco dos endpoints principais: `inicio`, `listarReservas`, `novaReserva`, `salvarReserva`, `editarReserva` e `excluirReserva`.
- **Roteiro Falado:** Para comecar, eu parti de um diagnostico de qualidade do sistema. O primeiro passo foi entender quais fluxos realmente importam para o usuario. No caso deste projeto, os fluxos principais sao listar reservas, cadastrar uma nova reserva, editar uma reserva existente e excluir uma reserva. Esses fluxos aparecem concentrados aqui no `ReservaController`, por isso eu estou mostrando os metodos com `@GetMapping` e `@PostMapping`.

  A partir dessa leitura, eu identifiquei alguns riscos de qualidade: ausencia inicial de testes automatizados, dependencia de validacoes no formulario, falta de pipeline e falta de indicadores para acompanhar a evolucao. Esse diagnostico e importante porque ele evita testar de forma aleatoria. Em vez disso, eu priorizo os pontos de maior impacto para o sistema. Agora que vimos onde estao os fluxos criticos, vamos passar para a estrategia de testes, que organiza como esses riscos serao tratados em camadas.

---

## Topico 2: Estrategia de Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `pom.xml`. Destaque as dependencias `spring-boot-starter-test` e `h2`, e depois o plugin `jacoco-maven-plugin`.
- **Roteiro Falado:** Dando continuidade ao diagnostico, a estrategia de testes foi organizada em uma piramide simples e adequada ao porte do projeto. Na base ficam os testes unitarios, que sao rapidos e validam comportamentos isolados. No meio ficam os testes de integracao, que verificam a comunicacao entre camadas, como controller, service e repository. No topo ficam os testes End-to-End, que simulam o fluxo completo do usuario.

  No `pom.xml`, eu mostro as ferramentas que sustentam essa estrategia. O `spring-boot-starter-test` traz JUnit, Mockito e recursos de teste do Spring. O H2 permite testar persistencia em banco de memoria, sem depender do banco real. O JaCoCo gera relatorio de cobertura, ajudando a medir quanto do codigo foi exercitado pelos testes. Com essa estrutura, a estrategia deixa de ser apenas teorica e passa a ter suporte tecnico no projeto. Com a estrategia definida, o proximo passo foi transformar os riscos em cenarios concretos de teste.

---

## Topico 3: Testes Manuais e Modelagem (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `src/main/resources/templates/formulario-reserva.html`. Destaque o bloco do formulario, principalmente os campos `nomeCliente`, `dataReserva`, `quantidadePessoas` com `min="1"`, e o `select` de `status`.
- **Roteiro Falado:** Agora que a estrategia esta definida, eu entro na parte de modelagem dos testes manuais. Aqui, a ideia foi usar tecnicas classicas para transformar o comportamento esperado do formulario em casos de teste objetivos. No formulario de reserva, existem campos obrigatorios como nome do cliente, data da reserva, quantidade de pessoas e status.

  Com particionamento de equivalencia, eu separo entradas validas e invalidas, por exemplo uma reserva com todos os campos preenchidos contra uma reserva sem nome. Com analise de valor limite, eu olho para o campo de quantidade de pessoas, principalmente o limite minimo igual a 1. Com tabela de decisao, eu combino campos preenchidos e vazios para verificar quando o cadastro deve ser aceito. E com transicao de estados, eu observo mudancas como sair de `Pendente` para `Confirmada`. Esses testes manuais ajudam a pensar antes de automatizar. Depois de modelar os cenarios, fica mais claro o que deve ser coberto pela automacao.

---

## Topico 4: Automacao de Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivos `src/test/java/com/exemplo/reservas/service/ReservaServiceTest.java`, `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java`, `src/test/java/com/exemplo/reservas/repository/ReservaRepositoryTest.java` e `e2e/playwright/tests/reserva-fluxo-critico.spec.js`. Destaque, respectivamente, os metodos `deveSalvarReserva`, `salvarReservaDeveRedirecionar`, `deveSalvarEBuscarReserva` e o teste `Fluxo critico: cadastrar e visualizar reserva na lista`.
- **Roteiro Falado:** Depois de definir os cenarios, eu implementei a automacao em camadas. Primeiro, no `ReservaServiceTest`, os testes unitarios verificam se o service chama corretamente o repository para salvar, listar, buscar e excluir reservas. Aqui o Mockito permite isolar o service, sem depender do banco.

  Em seguida, no `ReservaControllerWebTest`, o MockMvc valida rotas da aplicacao, como abrir a lista, carregar o formulario, salvar uma reserva e redirecionar corretamente. No `ReservaRepositoryTest`, o `DataJpaTest` valida se uma reserva pode ser salva e recuperada no banco de teste. Por fim, o teste Playwright cobre o fluxo completo do usuario: acessar o sistema, clicar em nova reserva, preencher o formulario, salvar e confirmar que o cliente aparece na lista. Essa combinacao reduz falso positivo porque valida tanto partes isoladas quanto o comportamento integrado. Com a automacao estruturada, eu tambem usei IA como apoio para ampliar ideias de cenarios.

---

## Topico 5: Uso de IA nos Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `qa/fase-5-uso-ia-nos-testes.md`. Destaque os blocos `Prompts sugeridos para gerar massa de teste`, `Limitacoes da IA` e `Onde a validacao humana e indispensavel`. Como exemplo de teste revisado, abra tambem `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java` e destaque o metodo `editarReservaComIdInexistente`.
- **Roteiro Falado:** Dando continuidade a logica de testes, a IA foi usada como ferramenta de apoio, nao como substituta da analise humana. A ideia foi usar prompts para gerar massa de dados, cenarios limite e sugestoes de regressao para o CRUD de reservas. Por exemplo, a IA pode sugerir casos com nome vazio, quantidade zero, status invalido ou id inexistente.

  Mas o ponto principal aqui e o controle. Toda sugestao precisa ser revisada para evitar regras inventadas ou cenarios que nao fazem sentido para o dominio. Um bom exemplo no codigo e o teste de editar reserva com id inexistente: ele valida um comportamento real do sistema, que e redirecionar para a lista sem quebrar a tela. Esse tipo de caso poderia ser sugerido por IA, mas a decisao de automatizar depende da leitura do codigo e da relevancia do risco. Assim, a IA acelera a criacao de ideias, enquanto a responsabilidade tecnica continua sendo da equipe. Depois desse apoio na criacao dos testes, o proximo passo e garantir que eles rodem automaticamente.

---

## Topico 6: Pipeline de Qualidade (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `.github/workflows/ci-qa.yml`. Destaque os jobs `build-test-quality` e `e2e-playwright`, especialmente os passos `Build and Test`, `Upload Surefire Reports`, `Upload JaCoCo Report`, `Start Application (CI Profile)` e `Run E2E Tests`.
- **Roteiro Falado:** Agora que existem testes automatizados, o pipeline de qualidade garante que eles sejam executados de forma padronizada a cada integracao de codigo. Neste arquivo do GitHub Actions, o primeiro job configura Java 17, executa `mvn -B clean test` e publica os relatorios do Surefire e do JaCoCo. Isso cobre os testes Java: unitarios, integracao web e persistencia.

  O segundo job depende do primeiro e executa os testes End-to-End. Ele configura Java e Node, empacota a aplicacao, instala as dependencias do Playwright, sobe a aplicacao com perfil de CI, espera o endpoint `/reservas` responder e depois roda o fluxo E2E. A vantagem dessa esteira e que a validacao deixa de depender apenas da execucao manual local. Se um teste falha, o pipeline gera evidencia para investigacao. Depois de garantir que o comportamento funcional roda no CI, faz sentido olhar tambem para aspectos nao funcionais, como desempenho.

---

## Topico 7: Testes Nao Funcionais (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `qa/fase-7-testes-nao-funcionais.md`. Destaque o bloco `Cenario inicial proposto`. Se quiser apontar o endpoint no codigo, abra `src/main/java/com/exemplo/reservas/controller/ReservaController.java` e destaque o metodo `listarReservas`, que atende `GET /reservas`.
- **Roteiro Falado:** Alem dos testes funcionais, o trabalho tambem planeja uma validacao nao funcional inicial. O foco escolhido foi desempenho, usando o endpoint principal `GET /reservas`, porque ele representa a tela de listagem e afeta diretamente a percepcao do usuario. O cenario proposto usa carga moderada, com 30 usuarios virtuais, ramp-up de 30 segundos e duracao de 3 minutos.

  As metas tambem foram definidas de forma objetiva: latencia p95 menor ou igual a 800 milissegundos e taxa de erro abaixo de 1%. Mesmo sendo uma avaliacao inicial, ela cria uma linha de base para comparacoes futuras. Se o sistema crescer, sera possivel verificar se novas alteracoes pioraram o tempo de resposta. Como evolucao, o documento tambem cita uma verificacao de seguranca com OWASP ZAP. Com isso, a qualidade passa a considerar nao so se a funcionalidade funciona, mas tambem se ela responde bem sob carga. Com o desempenho planejado, chegamos ao ultimo ponto: como acompanhar qualidade ao longo do tempo.

---

## Topico 8: Metricas e Governanca (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivos `pom.xml` e `.github/workflows/ci-qa.yml`. No `pom.xml`, destaque o plugin `jacoco-maven-plugin`. No workflow, destaque `Upload JaCoCo Report`, `Upload Surefire Reports`, `Upload Playwright Report` e `Quality Gate Placeholder`.
- **Roteiro Falado:** Para fechar os oito topicos, a parte de metricas e governanca transforma os testes em acompanhamento continuo. A cobertura de testes e medida pelo JaCoCo, configurado no `pom.xml`. Os relatorios de execucao sao publicados pelo pipeline, incluindo Surefire para os testes Java, JaCoCo para cobertura e Playwright para o E2E.

  Alem da cobertura, o trabalho define indicadores como densidade de defeitos, taxa de falhas em producao, sucesso dos testes E2E e MTTR, que e o tempo medio para correcao. A governanca entra nas regras objetivas: build quebrado bloqueia integracao, bug critico bloqueia release e regressao dos fluxos criticos deve ocorrer a cada entrega. Isso evita que qualidade seja tratada apenas no final. Ela passa a ser acompanhada durante o ciclo de desenvolvimento. Agora que percorremos diagnostico, estrategia, modelagem, automacao, IA, pipeline, nao funcionais e metricas, eu fecho com o impacto geral dessa abordagem no projeto.

---

## Conclusao (~30 segundos)
- **O que falar:** Concluindo, a estrategia de testes deste projeto criou uma base de qualidade progressiva para o Sistema de Reservas. Os testes unitarios validam a logica isolada, os testes de integracao verificam controller e persistencia, o E2E cobre o fluxo critico do usuario, e o pipeline automatiza a execucao e a coleta de evidencias. Com metricas e governanca, o projeto ganha mais confiabilidade, previsibilidade e menor risco de regressao. Portanto, a principal contribuicao foi transformar testes em um processo continuo de qualidade, e nao apenas em uma etapa final da entrega.