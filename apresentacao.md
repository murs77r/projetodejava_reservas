# Roteiro de Apresentação - Testes do Projeto

Tempo total estimado: 12 a 14 minutos.

## Introdução (~45 segundos a 1 minuto)
- **O que falar:** Bom dia. Este trabalho foi desenvolvido por Murilo Souza, João Paulo Nunes e Victor de Castro para a matéria de Java. O projeto é um Sistema de Reservas feito em Java com Spring Boot, com uma proposta simples e bem objetiva: permitir cadastrar, listar, editar e excluir reservas. Apesar de ser um sistema simples, ele foi estruturado com uma visão mais próxima de um projeto real, usando banco de dados, Docker, controle de versão e automações em ambiente de nuvem.

  Ao longo do desenvolvimento, nós fizemos melhorias para abarcar também a parte de testes e qualidade de software. Então, além de implementar o sistema em si, nós organizamos uma estratégia de qualidade para validar o comportamento da aplicação, reduzir risco de regressão e mostrar evidências de que o projeto funciona de forma confiável. A apresentação vai seguir as oito etapas documentadas no trabalho: diagnóstico, estratégia de testes, modelagem manual, automação, uso de IA, pipeline de qualidade, testes não funcionais e métricas de governança.

---

## Tópico 1: Diagnóstico de Qualidade (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `src/main/java/com/exemplo/reservas/controller/ReservaController.java`. Destaque o bloco dos endpoints principais: `inicio`, `listarReservas`, `novaReserva`, `salvarReserva`, `editarReserva` e `excluirReserva`.
- **Roteiro Falado:** Para começar, nós fizemos um diagnóstico de qualidade do sistema. A ideia foi entender quais eram os fluxos mais importantes para o usuário antes de sair criando testes. No caso deste projeto, os fluxos principais são bem claros: consultar as reservas cadastradas, criar uma nova reserva, alterar uma reserva existente e remover uma reserva quando necessário.

  Ao observar esses fluxos, identificamos alguns riscos importantes. O primeiro era a ausência de uma base automatizada de testes. O segundo era a dependência de validações feitas principalmente na tela. Também havia a necessidade de uma esteira de qualidade para validar o projeto de forma contínua e gerar evidências. Esse diagnóstico foi importante porque nos ajudou a testar com intenção, priorizando os pontos que poderiam causar mais impacto caso falhassem. Agora que vimos os principais riscos do sistema, vamos passar para a estratégia de testes, que organiza como esses riscos foram tratados em camadas.

---

## Tópico 2: Estratégia de Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `pom.xml`. Destaque as dependências `spring-boot-starter-test` e `h2`, e depois o plugin `jacoco-maven-plugin`.
- **Roteiro Falado:** Dando continuidade ao diagnóstico, nós organizamos a estratégia de testes usando uma pirâmide simples. Na base ficam os testes unitários, que são mais rápidos e ajudam a validar partes isoladas da aplicação. No meio ficam os testes de integração, que verificam se as camadas conversam corretamente entre si. No topo ficam os testes de ponta a ponta, que simulam a experiência real do usuário dentro do sistema.

  Essa divisão foi escolhida porque faz sentido para um projeto acadêmico e também para um projeto profissional de pequeno porte. Não adianta depender apenas de testes manuais, porque eles são mais lentos e mais sujeitos a esquecimento. Também não adianta ter só testes de tela, porque eles podem ser mais demorados e frágeis. Por isso, combinamos testes rápidos, testes de integração e testes de fluxo completo. Além disso, incluímos cobertura de testes com JaCoCo, para ter uma métrica objetiva sobre o que está sendo exercitado. Com a estratégia definida, o próximo passo foi transformar os riscos em cenários concretos de validação.

---

## Tópico 3: Testes Manuais e Modelagem (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `src/main/resources/templates/formulario-reserva.html`. Destaque o bloco do formulário, principalmente os campos `nomeCliente`, `dataReserva`, `quantidadePessoas` com `min="1"`, e o `select` de `status`.
- **Roteiro Falado:** Agora que a estratégia está definida, entramos na parte de modelagem dos testes manuais. Antes de automatizar, é importante pensar nos cenários de forma organizada. O formulário de reserva tem campos obrigatórios, como nome do cliente, data, quantidade de pessoas e status da reserva. A partir desses campos, aplicamos técnicas clássicas de modelagem.

  Com particionamento de equivalência, separamos entradas válidas e inválidas, como uma reserva completa em comparação com uma reserva sem nome. Com análise de valor limite, olhamos para casos próximos do limite permitido, principalmente na quantidade mínima de pessoas. Com tabela de decisão, combinamos campos preenchidos e não preenchidos para entender quando o cadastro deve ser aceito ou bloqueado. E com transição de estados, pensamos em mudanças de situação da reserva, como sair de pendente para confirmada. Essa modelagem dá base para uma automação mais consciente. Depois de estruturar os cenários manuais, passamos para a implementação dos testes automatizados.

---

## Tópico 4: Automação de Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivos `src/test/java/com/exemplo/reservas/service/ReservaServiceTest.java`, `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java`, `src/test/java/com/exemplo/reservas/repository/ReservaRepositoryTest.java` e `e2e/playwright/tests/reserva-fluxo-critico.spec.js`. Destaque, respectivamente, os métodos `deveSalvarReserva`, `salvarReservaDeveRedirecionar`, `deveSalvarEBuscarReserva` e o teste `Fluxo critico: cadastrar e visualizar reserva na lista`.
- **Roteiro Falado:** Depois de definir os cenários, nós automatizamos os testes em camadas. Primeiro, criamos testes unitários para verificar a lógica da camada de serviço. Esses testes são importantes porque executam rápido e ajudam a encontrar problemas cedo, sem precisar subir a aplicação inteira.

  Em seguida, criamos testes para validar o comportamento da parte web, verificando se as telas são carregadas, se o formulário é processado e se o usuário é direcionado corretamente depois das ações. Também criamos teste de persistência para confirmar que uma reserva consegue ser salva e recuperada no banco de teste. Por fim, automatizamos o fluxo principal com Playwright, simulando o caminho de um usuário real: abrir o sistema, cadastrar uma reserva e verificar se ela aparece na lista. Essa combinação é importante porque não valida só uma parte isolada. Ela mostra que a aplicação funciona desde a lógica interna até a experiência final do usuário. Com a automação estruturada, usamos também IA como apoio para ampliar ideias de cenários.

---

## Tópico 5: Uso de IA nos Testes (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `qa/fase-5-uso-ia-nos-testes.md`. Destaque os blocos `Prompts sugeridos para gerar massa de teste`, `Limitações da IA` e `Onde a validação humana é indispensável`. Como exemplo de teste revisado, abra também `src/test/java/com/exemplo/reservas/controller/ReservaControllerWebTest.java` e destaque o método `editarReservaComIdInexistente`.
- **Roteiro Falado:** Dando continuidade à lógica de testes, a inteligência artificial foi usada como uma ferramenta de apoio. Ela ajudou principalmente na geração de ideias, como massa de dados, cenários de exceção e possibilidades de regressão para o sistema de reservas.

  Mas o ponto mais importante é que a IA não substituiu a análise humana. Toda sugestão precisa ser revisada, porque a IA pode inventar regras que não existem, repetir casos parecidos ou ignorar detalhes do domínio. Então, nós usamos a IA como aceleradora de raciocínio, mas a decisão final sobre o que faz sentido testar continuou sendo nossa. Um exemplo disso é validar o comportamento quando alguém tenta editar uma reserva que não existe. Esse tipo de cenário é importante porque evita que o sistema quebre em uma situação inesperada. Depois desse apoio na criação e revisão de ideias, o próximo passo foi garantir que os testes rodassem automaticamente em um ambiente controlado.

---

## Tópico 6: Pipeline de Qualidade (~1.5 min)
- **O que abrir no VS Code:** Arquivo `.github/workflows/ci-qa.yml`. Destaque os jobs `build-test-quality` e `e2e-playwright`, especialmente os passos `Build and Test`, `Upload Surefire Reports`, `Upload JaCoCo Report`, `Start Application (CI Profile)` e `Run E2E Tests`.
- **Roteiro Falado:** Agora chegamos em uma das partes mais importantes para mostrar maturidade do projeto: o pipeline de qualidade com GitHub Actions. Mesmo sendo um trabalho acadêmico, nós não deixamos a validação depender apenas da máquina de quem desenvolveu. Criamos uma esteira automatizada em ambiente de nuvem, onde o código é baixado, o Java é configurado, as dependências são preparadas com cache e os testes são executados de forma padronizada.

  Esse pipeline faz mais do que simplesmente rodar testes. Ele cria uma rotina de integração contínua. Primeiro, ele compila o projeto e executa os testes Java, cobrindo unidade, integração web e persistência. Depois, ele publica relatórios como artefatos, incluindo evidências de execução e cobertura. Em seguida, existe uma segunda etapa voltada para testes de ponta a ponta: o ambiente Node é configurado, o Playwright é instalado, a aplicação é empacotada, iniciada em perfil de CI e só depois o fluxo crítico do usuário é executado.

  Isso é relevante porque aproxima o projeto de uma prática real de engenharia de software. Se alguém altera o código e quebra uma funcionalidade, a falha aparece no pipeline, com relatório, log e evidência para investigação. Ou seja, o GitHub Actions funciona como uma barreira automática de qualidade, reduzindo dependência de conferência manual e aumentando a confiabilidade da entrega. Depois de garantir essa validação funcional automatizada, passamos também a olhar para atributos não funcionais, como desempenho.

---

## Tópico 7: Testes Não Funcionais (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivo `qa/fase-7-testes-nao-funcionais.md`. Destaque o bloco `Cenário inicial proposto`. Se quiser apontar o endpoint no código, abra `src/main/java/com/exemplo/reservas/controller/ReservaController.java` e destaque o método `listarReservas`, que atende `GET /reservas`.
- **Roteiro Falado:** Além dos testes funcionais, o trabalho também propõe uma validação não funcional inicial. Aqui, o foco escolhido foi desempenho. A ideia é avaliar a tela principal de listagem, porque ela representa um ponto importante da experiência do usuário. Se essa tela demora muito ou falha sob carga, o sistema passa uma sensação de instabilidade, mesmo que as regras de negócio estejam corretas.

  O cenário proposto usa uma carga moderada, com usuários virtuais acessando o sistema por alguns minutos. Também foram definidas metas objetivas, como tempo de resposta dentro de um limite aceitável e baixa taxa de erro. Mesmo que seja uma validação inicial, ela cria uma referência para o futuro. Assim, se o sistema evoluir, poderemos comparar se novas alterações melhoraram ou pioraram o desempenho. Como extensão, também foi citada a possibilidade de uma análise de segurança com OWASP ZAP. Dessa forma, a qualidade passa a considerar não só se o sistema funciona, mas também se ele funciona bem em condições mais próximas do uso real.

---

## Tópico 8: Métricas e Governança (~1 a 1.5 min)
- **O que abrir no VS Code:** Arquivos `pom.xml` e `.github/workflows/ci-qa.yml`. No `pom.xml`, destaque o plugin `jacoco-maven-plugin`. No workflow, destaque `Upload JaCoCo Report`, `Upload Surefire Reports`, `Upload Playwright Report` e `Quality Gate Placeholder`.
- **Roteiro Falado:** Para fechar os oito tópicos, a parte de métricas e governança mostra que a qualidade não ficou só no discurso. Nós definimos indicadores para acompanhar o projeto de forma objetiva. A cobertura de testes é uma dessas métricas, porque mostra quanto do código está sendo exercitado. Os relatórios publicados pelo pipeline também ajudam a registrar evidências de execução, falhas, logs e resultados dos testes automatizados.

  Outro ponto importante é que o uso do GitHub Actions dá rastreabilidade. Cada execução fica associada a uma alteração no código, então é possível saber quando uma falha apareceu, em qual etapa ela ocorreu e quais evidências foram geradas. Isso traz uma postura mais profissional para o projeto, porque a qualidade deixa de depender apenas de opinião e passa a ser acompanhada por dados.

  Além disso, definimos políticas simples de governança: se a build quebra, a integração deve ser bloqueada; se existe defeito crítico, a entrega não deve ser liberada; e os fluxos principais precisam passar por regressão antes de uma versão final. Mesmo sendo estudantes, essa estrutura mostra preocupação com processo, rastreabilidade, confiabilidade e manutenção do sistema. Agora que percorremos diagnóstico, estratégia, modelagem, automação, IA, pipeline, testes não funcionais e métricas, eu encerro com o impacto geral dessa abordagem.

---

## Conclusão (~30 segundos)
- **O que falar:** Concluindo, a estratégia de testes deste projeto criou uma base de qualidade progressiva para o Sistema de Reservas. O sistema é simples, mas foi tratado com práticas importantes de engenharia: testes automatizados, validação em camadas, execução em pipeline na nuvem, geração de relatórios e definição de métricas de acompanhamento. Os testes unitários validam partes isoladas, os testes de integração verificam a comunicação entre camadas, o teste de ponta a ponta cobre o fluxo principal do usuário e o pipeline automatiza a validação do projeto. Com isso, o trabalho ganha mais confiabilidade, previsibilidade e menor risco de regressão. A principal contribuição foi transformar testes e qualidade em um processo contínuo, e não apenas em uma etapa final da entrega.