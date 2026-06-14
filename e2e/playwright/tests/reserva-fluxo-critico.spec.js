const { test, expect } = require('@playwright/test');

test('Fluxo critico: cadastrar e visualizar reserva na lista', async ({ page }) => {
  // 1) Abre o sistema na pagina principal.
  await page.goto('/');

  // 2) Vai para o formulario de nova reserva.
  await page.getByRole('link', { name: 'Nova Reserva' }).click();

  // 3) Preenche os campos obrigatorios do cadastro.
  await page.locator('#nomeCliente').fill('Cliente E2E');
  await page.locator('#dataReserva').fill('2026-12-20');
  await page.locator('#quantidadePessoas').fill('3');
  await page.locator('#status').selectOption('Confirmada');

  // 4) Salva e valida se voltou para a lista.
  await page.getByRole('button', { name: 'Salvar' }).click();
  await expect(page).toHaveURL(/\/reservas$/);

  // 5) Garante que a reserva criada aparece na tabela.
  await expect(page.getByText('Cliente E2E')).toBeVisible();
});
