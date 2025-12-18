package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public void pay(@RequestParam Long reservationId) {
        paymentService.pay(reservationId);
    }
}
