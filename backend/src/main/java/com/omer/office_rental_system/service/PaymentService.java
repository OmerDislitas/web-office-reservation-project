package com.omer.office_rental_system.service;

import com.omer.office_rental_system.entity.*;
import com.omer.office_rental_system.exception.ConflictException;
import com.omer.office_rental_system.repo.PaymentRepo;
import com.omer.office_rental_system.repo.ReservationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final ReservationRepo reservationRepo;

    public void pay(Long reservationId) {

        if (paymentRepo.findByReservationId(reservationId).isPresent()) {
            throw new ConflictException("Payment already exists for this reservation");
        }

        Reservation r = reservationRepo.findById(reservationId).orElseThrow();

        Payment p = new Payment();
        p.setReservation(r);
        p.setAmount(r.getTotalPrice());
        p.setStatus(PaymentStatus.SIMULATED);
        p.setPaidAt(LocalDateTime.now());

        paymentRepo.save(p);
    }
}
