package com.exemplo.reservas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.reservas.model.Reserva;
import com.exemplo.reservas.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    // Cria uma reserva de exemplo para reduzir repeticao nos testes.
    private Reserva criarReservaExemplo() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNomeCliente("Ana");
        reserva.setDataReserva(LocalDate.of(2026, 6, 20));
        reserva.setQuantidadePessoas(3);
        reserva.setStatus("Confirmada");
        return reserva;
    }

    @Test
    @DisplayName("Deve salvar reserva chamando o repository")
    void deveSalvarReserva() {
        Reserva entrada = criarReservaExemplo();
        when(reservaRepository.save(any(Reserva.class))).thenReturn(entrada);

        Reserva resultado = reservaService.salvar(entrada);

        assertEquals("Ana", resultado.getNomeCliente());
        verify(reservaRepository, times(1)).save(entrada);
    }

    @Test
    @DisplayName("Deve listar todas as reservas")
    void deveListarTodas() {
        when(reservaRepository.findAll()).thenReturn(List.of(criarReservaExemplo()));

        List<Reserva> resultado = reservaService.listarTodas();

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar reserva por id")
    void deveBuscarPorId() {
        Reserva reserva = criarReservaExemplo();
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Confirmada", resultado.get().getStatus());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve excluir reserva por id")
    void deveExcluirPorId() {
        reservaService.excluir(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }
}
