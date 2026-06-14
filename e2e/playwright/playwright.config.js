// Configuracao simples para testes E2E locais.
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: './tests',
  timeout: 30_000,
  retries: 0,
  use: {
    baseURL: 'http://localhost:8080',
    headless: true,
    trace: 'on-first-retry'
  }
});
