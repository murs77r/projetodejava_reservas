package com.exemplo.reservas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.exemplo.reservas.model.Reserva;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("Deve salvar e recuperar reserva no banco de teste")
    void deveSalvarEBuscarReserva() {
        // Prepara e salva uma reserva para validar o fluxo de persistencia.
        Reserva reserva = new Reserva();
        reserva.setNomeCliente("Diego");
        reserva.setDataReserva(LocalDate.of(2026, 9, 15));
        reserva.setQuantidadePessoas(5);
        reserva.setStatus("Confirmada");

        Reserva salva = reservaRepository.save(reserva);
        Optional<Reserva> buscada = reservaRepository.findById(salva.getId());

        assertTrue(buscada.isPresent());
        assertEquals("Diego", buscada.get().getNomeCliente());
    }
}
