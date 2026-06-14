# Estrategia de QA (Quality Assurance, ou Garantia de Qualidade) para o Sistema de Reservas

## Introducao
Este trabalho apresenta a estruturacao de Quality Assurance (Garantia de Qualidade) em um sistema Java ja existente, com foco em reduzir falhas e retrabalho sem alterar as regras de negocio principais. A proposta foi organizar um processo de qualidade completo, cobrindo diagnostico, estrategia de testes, automacao, uso de IA (Inteligencia Artificial), pipeline de validacao, testes nao funcionais e metricas de governanca.

## 1. Diagnostico de Qualidade
O diagnostico inicial identificou os fluxos principais do sistema: listar, cadastrar, editar e excluir reservas. A partir desses fluxos, os riscos mais relevantes foram:
1. Ausencia de testes automatizados.
2. Dependencia de validacoes no front-end.
3. Falta de pipeline de qualidade.
4. Ausencia de indicadores para acompanhamento continuo.

Esses pontos aumentam a chance de regressao e dificultam a deteccao antecipada de defeitos. A conclusao foi que o sistema funciona, mas precisava de uma base de QA (Garantia de Qualidade) para evoluir com seguranca.

## 2. Estrategia de Testes
A estrategia adotou uma piramide de testes adequada ao contexto academico e ao porte do projeto:
1. Testes unitarios para validar comportamento isolado.
2. Testes de integracao para validar comunicacao entre camadas.
3. Testes End-to-End (de ponta a ponta) para validar fluxo critico do usuario.

Tambem foram definidos criterios simples de execucao:
1. Entrada: ambiente de teste disponivel e cenario definido.
2. Aceite: fluxo executado sem falhas bloqueantes.
3. Saida: resultado registrado com evidencias e classificacao de defeitos.

A regressao foi planejada em tres momentos: por alteracao de codigo, consolidacao periodica e validacao final antes de entrega.

## 3. Testes Manuais e Modelagem
Foram aplicadas tecnicas classicas de modelagem para garantir qualidade dos cenarios:
1. Particionamento de equivalencia.
2. Analise de valor limite.
3. Tabela de decisao.
4. Transicao de estados.

Essas tecnicas permitiram organizar casos de teste formais e padronizar a abertura de defeitos com severidade, prioridade, passos de reproducao e evidencias.

## 4. Automacao de Testes
A automacao foi implementada em camadas complementares:
1. Testes unitarios para a logica de servico.
2. Testes de integracao web para rotas e comportamento do controlador.
3. Testes de persistencia em banco de teste.
4. Teste End-to-End (de ponta a ponta) do fluxo critico de cadastro e listagem.

Essa combinacao oferece cobertura mais equilibrada e reduz risco de falso positivo, pois valida tanto partes isoladas quanto o comportamento integrado do sistema.

## 5. Uso de IA nos Testes
A IA foi utilizada como apoio para sugerir massa de dados e cenarios de excecao. O uso foi controlado e revisado manualmente, considerando limitacoes como:
1. Possivel geracao de regras inexistentes.
2. Falta de contexto completo do dominio.
3. Redundancia de cenarios.

Portanto, a IA (Inteligencia Artificial) foi tratada como ferramenta de apoio, e nao como substituta da analise de QA (Garantia de Qualidade).

## 6. Pipeline de Qualidade
Foi definida uma esteira de CI/CD (Integracao Continua e Entrega Continua) para automatizar validacoes em cada integracao de codigo. O pipeline contempla build (processo de compilacao e empacotamento), execucao de testes e geracao de relatorios. Esse processo melhora a confiabilidade da entrega e cria rastreabilidade dos resultados de qualidade.

## 7. Testes Nao Funcionais
No escopo nao funcional, foi planejada validacao inicial de desempenho com carga moderada em endpoint critico. O objetivo e estabelecer uma referencia de latencia e taxa de erro para acompanhar evolucao futura.

Como extensao, a proposta inclui avaliacao de seguranca em etapa posterior.

## 8. Metricas e Governanca
Para acompanhamento continuo, foram definidos quatro indicadores principais:
1. Cobertura de testes.
2. Densidade de defeitos.
3. Taxa de falhas em producao.
4. MTTR (Mean Time to Repair, ou Tempo Medio para Correcao).

Com base nesses indicadores, foi proposta uma politica objetiva: falha de build bloqueia integracao e defeito critico bloqueia liberacao.

## Conclusao
O trabalho demonstra que e possivel evoluir a qualidade de um sistema existente com baixa intervencao no codigo de negocio, desde que haja planejamento, automacao e acompanhamento por metricas. A principal contribuicao foi transformar qualidade em processo continuo, com ganhos esperados de confiabilidade, previsibilidade e reducao de retrabalho.

## Glossario de Siglas
1. QA: Quality Assurance (Garantia de Qualidade).
2. IA: Inteligencia Artificial.
3. End-to-End: teste de ponta a ponta, simulando o fluxo completo do usuario.
4. CI/CD: Integracao Continua e Entrega Continua.
5. MTTR: Mean Time to Repair (Tempo Medio para Correcao).
