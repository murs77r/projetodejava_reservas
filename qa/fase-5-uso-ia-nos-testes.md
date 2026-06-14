# FASE 5 - Uso de IA nos Testes

## 1. Prompts sugeridos para gerar massa de teste

### Prompt 1 - Massa valida variada
"Gere 30 exemplos de reservas em JSON com os campos nomeCliente, dataReserva, quantidadePessoas e status (Confirmada/Pendente), variando tamanhos de nome, datas e quantidade de pessoas."

### Prompt 2 - Casos de excecao (edge cases)
"Gere cenarios limite para cadastro de reservas: nomes vazios, data passada, quantidade zero, quantidade muito alta, status fora da lista. Entregue em tabela com entrada e resultado esperado."

### Prompt 3 - Regressao focada
"Crie 15 cenarios de regressao para CRUD de reservas em Spring Boot, incluindo criacao, edicao, exclusao, comportamento com id inexistente e validacao de exibicao na lista."

## 2. Como usar com responsabilidade
1. Usar IA para acelerar ideias de casos de teste.
2. Revisar manualmente cada caso antes de automatizar.
3. Rastrear quais casos vieram de IA e quais foram ajustados.

## 3. Limitacoes da IA
1. Pode inventar regras de negocio que nao existem no sistema.
2. Pode ignorar detalhes de ambiente e infraestrutura.
3. Pode gerar casos redundantes ou superficiais.
4. Pode deixar passar cenarios de alto risco especificos do dominio.

## 4. Onde a validacao humana e indispensavel
1. Priorizacao por impacto no negocio.
2. Definicao de severidade e prioridade de bugs.
3. Aprovacao de criterios de aceite e qualidade.
4. Revisao final de cobertura de riscos.
