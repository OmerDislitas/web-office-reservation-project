package com.officerental.backend.controller;

import com.officerental.backend.repository.OfficeRepository;
import com.officerental.backend.repository.ReservationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OfficeRepository officeRepository;
    private final ReservationRepository reservationRepository;

    public AdminController(OfficeRepository officeRepository, ReservationRepository reservationRepository) {
        this.officeRepository = officeRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("officeCount", officeRepository.count());
        stats.put("pendingReservationCount", reservationRepository.countByStatus("PENDING"));
        return stats;
    }
}