package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.entity.Reservation;
import com.omer.office_rental_system.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String list(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "admin-reservations";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/admin/reservations";
    }
}
