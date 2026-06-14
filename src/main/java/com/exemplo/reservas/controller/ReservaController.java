package com.exemplo.reservas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.exemplo.reservas.model.Reserva;
import com.exemplo.reservas.service.ReservaService;

@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Abre direto a tela principal (lista)
    @GetMapping("/")
    public String inicio() {
        return "redirect:/reservas";
    }

    // Lista todas as reservas
    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "lista-reservas";
    }

    // Abre o formulario para cadastrar nova reserva
    @GetMapping("/reservas/nova")
    public String novaReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "formulario-reserva";
    }

    // Salva nova reserva ou edicao
    @PostMapping("/reservas/salvar")
    public String salvarReserva(@ModelAttribute Reserva reserva) {
        reservaService.salvar(reserva);
        return "redirect:/reservas";
    }

    // Carrega uma reserva existente no formulario
    @GetMapping("/reservas/editar/{id}")
    public String editarReserva(@PathVariable Long id, Model model) {
        return reservaService.buscarPorId(id)
                .map(reserva -> {
                    model.addAttribute("reserva", reserva);
                    return "formulario-reserva";
                })
                .orElse("redirect:/reservas");
    }

    // Exclui a reserva pela lista
    @GetMapping("/reservas/excluir/{id}")
    public String excluirReserva(@PathVariable Long id) {
        reservaService.excluir(id);
        return "redirect:/reservas";
    }
}
