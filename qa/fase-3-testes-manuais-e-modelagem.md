# FASE 3 - Testes Manuais e Modelagem

## 1. Casos de teste formais

| ID | Tecnica | Cenario | Entrada | Resultado Esperado | Prioridade |
|---|---|---|---|---|---|
| CT-01 | Particionamento de Equivalencia | Cadastrar reserva valida | Nome preenchido, data futura, qtd>=1, status valido | Reserva salva e exibida na lista | Alta |
| CT-02 | Particionamento de Equivalencia | Nome vazio | nomeCliente vazio | Bloqueio no formulario | Alta |
| CT-03 | Valor Limite | Quantidade minima | quantidadePessoas=1 | Cadastro aceito | Alta |
| CT-04 | Valor Limite | Quantidade abaixo do minimo | quantidadePessoas=0 | Bloqueio no formulario | Alta |
| CT-05 | Tabela de Decisao | Combinacao de campos obrigatorios | Nome/data/qtd/status em combinacoes validas/invalidas | Apenas combinacao valida deve permitir salvar | Alta |
| CT-06 | Transicao de Estados | Alterar status de Pendente para Confirmada | Reserva existente em status Pendente | Status alterado e persistido | Media |
| CT-07 | Particionamento de Equivalencia | Editar reserva inexistente | id nao encontrado | Redireciona para lista sem quebrar tela | Media |
| CT-08 | Particionamento de Equivalencia | Excluir reserva existente | id valido | Reserva removida da listagem | Alta |

## 2. Modelo de Relatorio de Bug

### Bug ID
BUG-XXXX

### Titulo
Resumo curto e objetivo do defeito.

### Descricao
Explicar o que aconteceu e o que era esperado.

### Severidade
- Critica
- Alta
- Media
- Baixa

### Prioridade
- P1 (urgente)
- P2 (alta)
- P3 (media)
- P4 (baixa)

### Ambiente
Ex.: Docker local, navegador Chrome 126, Java 17.

### Passos para Reproducao
1. Acessar tela X.
2. Preencher campo Y com valor Z.
3. Clicar no botao Salvar.

### Resultado Atual
Descrever comportamento atual observado.

### Resultado Esperado
Descrever comportamento esperado.

### Evidencias
- Screenshot
- Video curto
- Log de aplicacao

### Status
Aberto / Em analise / Em correcao / Resolvido / Reaberto / Fechado
