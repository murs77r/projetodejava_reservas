package com.exemplo.reservas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exemplo.reservas.model.Reserva;
import com.exemplo.reservas.service.ReservaService;

@WebMvcTest(ReservaController.class)
class ReservaControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    // Reserva de exemplo para popular model e mocks de forma didatica.
    private Reserva criarReservaExemplo() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNomeCliente("Bruno");
        reserva.setDataReserva(LocalDate.of(2026, 7, 1));
        reserva.setQuantidadePessoas(2);
        reserva.setStatus("Pendente");
        return reserva;
    }

    @Test
    @DisplayName("GET / deve redirecionar para /reservas")
    void inicioDeveRedirecionarParaLista() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservas"));
    }

    @Test
    @DisplayName("GET /reservas deve renderizar lista-reservas")
    void listarReservasDeveRenderizarLista() throws Exception {
        when(reservaService.listarTodas()).thenReturn(List.of(criarReservaExemplo()));

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-reservas"))
                .andExpect(model().attributeExists("reservas"));
    }

    @Test
    @DisplayName("GET /reservas/nova deve renderizar formulario-reserva")
    void novaReservaDeveRenderizarFormulario() throws Exception {
        mockMvc.perform(get("/reservas/nova"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario-reserva"))
                .andExpect(model().attributeExists("reserva"));
    }

    @Test
    @DisplayName("POST /reservas/salvar deve salvar e redirecionar")
    void salvarReservaDeveRedirecionar() throws Exception {
        when(reservaService.salvar(any(Reserva.class))).thenReturn(criarReservaExemplo());

        mockMvc.perform(post("/reservas/salvar")
                        .param("nomeCliente", "Carla")
                        .param("dataReserva", "2026-08-10")
                        .param("quantidadePessoas", "4")
                        .param("status", "Confirmada"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservas"));

        verify(reservaService, times(1)).salvar(any(Reserva.class));
    }

    @Test
    @DisplayName("GET /reservas/editar/{id} com id existente deve abrir formulario")
    void editarReservaComIdExistente() throws Exception {
        when(reservaService.buscarPorId(1L)).thenReturn(Optional.of(criarReservaExemplo()));

        mockMvc.perform(get("/reservas/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario-reserva"))
                .andExpect(model().attributeExists("reserva"));
    }

    @Test
    @DisplayName("GET /reservas/editar/{id} inexistente deve redirecionar")
    void editarReservaComIdInexistente() throws Exception {
        when(reservaService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservas/editar/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservas"));
    }

    @Test
    @DisplayName("GET /reservas/excluir/{id} deve excluir e redirecionar")
    void excluirReservaDeveRedirecionar() throws Exception {
        mockMvc.perform(get("/reservas/excluir/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservas"));

        verify(reservaService, times(1)).excluir(1L);
    }
}
