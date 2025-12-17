package com.officerental.backend.controller;

import com.officerental.backend.model.dto.ReservationCreateRequest;
import com.officerental.backend.model.entity.Reservation;
import com.officerental.backend.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // USER: rezervasyon oluşturur (login gerekli)
    @PostMapping
    public Reservation create(@RequestBody ReservationCreateRequest req, Principal principal) {
        return reservationService.create(req, principal.getName()); // name = email
    }

    // ADMIN: pending liste
    @GetMapping("/pending")
    public List<Reservation> pending() {
        return reservationService.getAllPending();
    }

    // ADMIN: approve/reject
    @PostMapping("/{id}/approve")
    public Reservation approve(@PathVariable Long id) {
        return reservationService.approve(id);
    }

    @PostMapping("/{id}/reject")
    public Reservation reject(@PathVariable Long id) {
        return reservationService.reject(id);
    }

    // USER: get own reservations
    @GetMapping("/my")
    public List<Reservation> myReservations(Principal principal) {
        return reservationService.getUserReservations(principal.getName());
    }
}
