package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.omer.office_rental_system.dto.reservation.MyReservationResponse;
import java.util.List;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

@GetMapping("/mine")
public List<MyReservationResponse> mine() {
    return reservationService.getMyReservations();
}
    private final ReservationService reservationService;

    @PostMapping
    public Map<String, Long> reserve(@RequestParam Long officeId,
                                    @RequestParam String startDate,
                                    @RequestParam String endDate) {

        Long id = reservationService.createReservation(
                officeId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );

        return Map.of("reservationId", id);
    }
}
