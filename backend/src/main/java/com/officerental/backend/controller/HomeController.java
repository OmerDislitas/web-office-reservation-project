package com.officerental.backend.controller;

import com.officerental.backend.repository.OfficeRepository;
import com.officerental.backend.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final OfficeRepository officeRepository;
    private final ReservationRepository reservationRepository;

    public HomeController(OfficeRepository officeRepository, ReservationRepository reservationRepository) {
        this.officeRepository = officeRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("officeCount", officeRepository.count());
        model.addAttribute("pendingReservationCount", reservationRepository.countByStatus("PENDING"));
        return "admin-home"; // templates/admin-home.html dosyasını arar
    }
}
