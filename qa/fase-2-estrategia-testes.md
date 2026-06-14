# FASE 2 - Estrategia de Testes

## 1. Objetivo
Construir uma estrategia de testes balanceada para prevenir regressao e reduzir falhas em producao.

## 2. Escopo de testes
- Funcional: fluxos CRUD de reservas
- Integracao: controller, serializacao e persistencia
- E2E: fluxo completo de cadastro e exibicao na lista
- Nao funcional: performance inicial do endpoint principal

## 3. Piramide de testes (proposta)
1. 70% Unitarios (Service e regras de validacao futuras)
2. 20% Integracao (Controller + Repository)
3. 10% E2E (fluxos criticos de negocio)

Justificativa tecnica:
- Unitarios sao rapidos e baratos para detectar regressao cedo.
- Integracao valida contrato entre camadas e banco.
- E2E garante experiencia real do usuario nos fluxos de maior risco.

## 4. Criterios de entrada
1. Build compilando localmente.
2. Ambiente de teste definido (H2 para testes automatizados).
3. Casos de teste revisados e priorizados.

## 5. Criterios de aceite
1. Todos os testes do pipeline em verde.
2. Sem falhas criticas nos fluxos CRUD.
3. Evidencia de cobertura e relatorios publicados.

## 6. Criterios de saida
1. Regressao principal executada.
2. Bugs criticos bloqueantes resolvidos.
3. Relatorio de execucao registrado.

## 7. Estrategia de regressao
1. Regressao rapida por commit: unitarios + web integration.
2. Regressao diaria: suite completa Maven + cobertura.
3. Regressao de release: incluir E2E e teste nao funcional escolhido.

## 8. Priorizacao por risco
1. Cadastro de reserva (alto valor para o negocio).
2. Edicao e exclusao (risco de perda/inconsistencia de dados).
3. Listagem (risco de indisponibilidade percebida pelo usuario).
