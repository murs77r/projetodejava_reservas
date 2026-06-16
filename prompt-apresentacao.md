# Prompt para IA — Reescrever `apresentacao.md` como roteiro oral universitário

Quero que você reescreva o arquivo `apresentacao.md` para que ele deixe de ser apenas um texto técnico e passe a funcionar **verdadeiramente como um roteiro escrito de fala** para um estudante universitário durante uma apresentação.

## Objetivo principal
Transforme o conteúdo em um **roteiro oral natural, fluido, organizado e convincente**, com linguagem de apresentação acadêmica. O texto precisa soar como algo que **eu realmente vou falar em voz alta**, e não como documentação técnica, relatório, checklist ou anotações soltas.

## Regras gerais obrigatórias
1. **Escreva em Português do Brasil.**
2. O resultado deve parecer fala de estudante universitário em apresentação formal.
3. O texto deve ser **natural, oral, encadeado e explicativo**, com transições claras entre partes.
4. Evite estilo excessivamente telegráfico.
5. Evite frases frias como “Mostro”, “Explico”, “Aponto”, “Detalho” isoladamente. Em vez disso, construa frases completas e naturais, como:
   - “Nesta parte, eu apresento...”
   - “O ponto principal aqui é...”
   - “Na prática, isso funciona da seguinte forma...”
   - “Isso é importante porque...”
6. O texto precisa ter **abertura, desenvolvimento, transições e fechamento**.
7. O conteúdo deve demonstrar domínio, segurança e clareza, mas ainda com voz compatível com a de um estudante universitário.
8. **Não invente funcionalidades, arquivos, fluxos ou tecnologias** que não existam no projeto.
9. Sempre que citar implementação, mantenha aderência ao código real do repositório.
10. Se houver trechos técnicos complexos, explique de forma que a professora perceba conhecimento sólido, especialmente na parte de Java e orientação a objetos.

## Estrutura obrigatória do novo `apresentacao.md`
O arquivo deve manter **segregação clara entre três grandes blocos**:

### 1. Parte geral
Esta seção deve apresentar:
- contexto do sistema;
- objetivo do projeto;
- problema que o sistema resolve;
- fluxo principal de uso;
- principais funcionalidades;
- visão geral da aplicação.

Essa parte deve ser compreensível para qualquer pessoa ouvindo a apresentação, mesmo sem entrar profundamente nas disciplinas.

### 2. Parte da matéria: Linguagem de Programação Orientada a Objetos II
Esta seção deve ser **descritiva, detalhada e oralizável**, pensada para eu ler em voz alta e a professora perceber que eu entendo muito bem os conceitos de Java, mesmo sendo estudante.

Aqui você deve:
- explicar a arquitetura do sistema de forma fluida;
- descrever com profundidade o papel de cada camada e componente relevante;
- explorar com riqueza de detalhes os conceitos de Java realmente presentes no projeto;
- comentar encapsulamento, classes, atributos, métodos, responsabilidades, separação em camadas, fluxo entre controller/service/repository/entity, persistência, tipagem, organização do código, uso de anotações, injeção de dependência, mapeamento objeto-relacional, coleções, tratamento do fluxo e demais pontos reais do projeto;
- fazer isso em formato de fala, e não como lista seca;
- soar como alguém que compreende o que está explicando.

Importante: essa parte deve ter **tom forte de domínio técnico**, mas sem parecer texto artificial ou professoral demais. Precisa ser algo plausível para um estudante muito bem preparado apresentar.

### 3. Parte da matéria: Testes / Qualidade
Esta seção deve continuar separada da parte de POO II.

Ela precisa ser escrita como roteiro de apresentação oral e deve considerar **explicitamente as 8 fases do arquivo `testes.md`**. Ou seja:
- use o arquivo `testes.md` como base obrigatória;
- incorpore as 8 fases na narrativa;
- organize a fala de modo que a evolução do processo de testes fique clara;
- explique cada fase de forma apresentável, natural e com encadeamento;
- relacione essas fases com os testes reais do projeto, sempre que houver correspondência no repositório.

Essa seção deve mostrar maturidade sobre:
- estratégia de testes;
- tipos de testes;
- objetivo de cada camada validada;
- integração com pipeline/automação, se existir;
- noção de qualidade de software;
- por que a abordagem adotada é importante para confiabilidade do sistema.

## Arquivo adicional obrigatório: `trecho.md`
Além de reescrever o `apresentacao.md`, você deve também gerar **um segundo arquivo chamado `trecho.md`**.

### Finalidade do `trecho.md`
Esse arquivo deve reunir **todos os trechos de código mencionados ou referenciados na apresentação principal**.

### Regras para o `trecho.md`
1. Coloque os trechos organizados por seção.
2. Cada trecho deve indicar claramente:
   - arquivo de origem;
   - finalidade do trecho;
   - relação com a fala do roteiro.
3. Não coloque explicações longas demais nesse arquivo; ele serve como apoio.
4. Os trechos devem ser reais e consistentes com o projeto.
5. O `apresentacao.md` deve mencionar ou sinalizar os momentos em que o apresentador pode consultar/mostrar trechos que estarão reunidos no `trecho.md`.
6. O `trecho.md` deve funcionar como material complementar, sem substituir o roteiro principal.

## Estilo esperado do novo `apresentacao.md`
- Deve parecer um **roteiro oral pronto para leitura e apresentação**.
- Pode usar subtítulos para organizar.
- Pode usar marcações discretas como:
  - “[neste momento, mostrar a tela...]”
  - “[aqui, posso apontar o trecho correspondente no `trecho.md`]”
- Não exagere nessas marcações.
- Priorize frases completas, boa cadência e progressão lógica.
- Faça transições elegantes entre parte geral, POO II e testes.

## O que evitar
- Não transformar o arquivo em documentação técnica fria.
- Não resumir demais.
- Não encher de listas secas.
- Não deixar a parte de POO II superficial.
- Não misturar a parte geral com as duas matérias.
- Não ignorar as 8 fases de `testes.md`.
- Não omitir o `trecho.md`.
- Não usar exemplos genéricos desconectados do projeto.

## Resultado esperado
Ao final, quero:
1. um novo `apresentacao.md` com cara de **fala universitária real**;
2. separação clara entre:
   - parte geral,
   - parte de Linguagem de Programação Orientada a Objetos II,
   - parte de Testes/Qualidade;
3. uma parte de POO II forte, descritiva e convincente em Java;
4. uma parte de testes construída considerando as **8 fases do `testes.md`**;
5. um arquivo `trecho.md` com os trechos de código usados como apoio à apresentação.
