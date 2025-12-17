package com.officerental.backend.controller;

import com.officerental.backend.model.entity.Reservation;
import com.officerental.backend.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String reservationsPage(Model model) {
        List<Reservation> pendingReservations = reservationService.getAllPending();
        model.addAttribute("reservations", pendingReservations);
        return "admin-reservations";
    }
}