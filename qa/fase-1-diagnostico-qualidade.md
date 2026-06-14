# FASE 1 - Diagnostico de Qualidade

## 1. Objetivo
Mapear os principais riscos de qualidade do Sistema de Reservas sem alterar regras de negocio.

## 2. Escopo analisado
- Camada Web MVC (Controller + Thymeleaf)
- Camada de servico
- Camada de persistencia (JPA + MySQL)
- Execucao local e via Docker

## 3. Funcionalidades principais
1. Listar reservas
2. Cadastrar reserva
3. Editar reserva
4. Excluir reserva

## 4. Modulos criticos
1. Controller de reservas: concentracao dos fluxos principais de CRUD.
2. Service/Repository: persistencia e consulta de dados.
3. Templates HTML: experiencia do usuario e validacao inicial de campos.
4. Configuracao de banco: disponibilidade do sistema.

## 5. Riscos (impacto x probabilidade)
| Risco | Impacto | Probabilidade | Nivel | Mitigacao proposta |
|---|---|---|---|---|
| Ausencia de testes automatizados | Alto | Alto | Critico | Criar piramide de testes (unit, integracao, E2E) |
| Validacao fraca no backend | Alto | Medio/Alto | Alto | Adicionar testes de cenarios invalidos e, no futuro, Bean Validation |
| Exclusao por GET | Medio/Alto | Medio | Alto | Cobrir via teste e planejar migracao para POST/DELETE |
| Credenciais fixas root/root | Alto | Alto | Critico | Variaveis de ambiente e segregacao por ambiente |
| Falta de pipeline de qualidade | Alto | Alto | Critico | CI no GitHub Actions com build, testes e artefatos |
| Falta de monitoramento de qualidade | Medio | Alto | Alto | Dashboard com cobertura, defeitos, falhas e MTTR |

## 6. Debitos tecnicos observados
1. Sem testes unitarios, integracao e E2E.
2. Sem quality gate automatizado.
3. Sem padrao de metricas de qualidade.
4. Dependencia de banco com credenciais hardcoded para execucao simples.

## 7. Acoes imediatas aplicadas nesta entrega
1. Base de testes JUnit + Mockito + MockMvc + DataJpaTest.
2. Banco H2 para testes.
3. Cobertura JaCoCo.
4. Workflow de CI no GitHub Actions.
5. Base E2E com Playwright para fluxo critico de cadastro.

## 8. Diferenciais sugeridos (nota maxima)
- SonarQube/SonarCloud para analise estatica.
- Abordagem TDD para novas regras.
- BDD leve em cenarios criticos com Given/When/Then.
