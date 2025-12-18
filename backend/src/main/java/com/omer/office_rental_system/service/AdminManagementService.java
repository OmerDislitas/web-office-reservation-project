package com.omer.office_rental_system.service;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.entity.Reservation;
import com.omer.office_rental_system.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManagementService {

    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final ReservationRepo reservationRepo;
    private final PaymentRepo paymentRepo;
    private final ReviewRepo reviewRepo;

    @Transactional
    public void deleteReservation(Long reservationId) {
        paymentRepo.findByReservationId(reservationId).ifPresent(paymentRepo::delete);
        reservationRepo.deleteById(reservationId);
    }

    @Transactional
    public void deleteOffice(Long officeId) {
        // 1) payments -> reservations
        List<Reservation> reservations = reservationRepo.findByOfficeId(officeId);
        for (Reservation r : reservations) {
            paymentRepo.findByReservationId(r.getId()).ifPresent(paymentRepo::delete);
        }
        reservationRepo.deleteAll(reservations);

        // 2) reviews
        reviewRepo.deleteByOfficeId(officeId);

        // 3) office
        officeRepo.deleteById(officeId);
    }

    @Transactional
    public void deleteUser(Long userId) {
        // Kullanıcının oluşturduğu office’leri de silelim (createdBy varsa)
        // (İstersen bunu kaldırırız ama “delete user” güvenli olsun diye ekledim.)
        List<Office> createdOffices = officeRepo.findByCreatedById(userId);
        for (Office o : createdOffices) {
            deleteOffice(o.getId());
        }

        // user reservations -> payments
        List<Reservation> reservations = reservationRepo.findByUserId(userId);
        for (Reservation r : reservations) {
            paymentRepo.findByReservationId(r.getId()).ifPresent(paymentRepo::delete);
        }
        reservationRepo.deleteAll(reservations);

        // user reviews
        reviewRepo.deleteByUserId(userId);

        // user
        userRepo.deleteById(userId);
    }
}
