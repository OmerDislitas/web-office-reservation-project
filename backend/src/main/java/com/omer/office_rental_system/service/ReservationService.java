package com.omer.office_rental_system.service;

import com.omer.office_rental_system.dto.reservation.MyReservationResponse;
import java.util.stream.Collectors;

import com.omer.office_rental_system.entity.*;
import com.omer.office_rental_system.exception.ConflictException;
import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.repo.PaymentRepo;
import com.omer.office_rental_system.repo.ReservationRepo;
import com.omer.office_rental_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.omer.office_rental_system.exception.ConflictException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;

    public Long createReservation(Long officeId, LocalDate start, LocalDate end) {

        if (end.isBefore(start) || end.isEqual(start)) {
            throw new RuntimeException("Invalid date range");
        }

        List<Reservation> conflicts = reservationRepo.findConflictingReservations(officeId, start, end);

        if (!conflicts.isEmpty()) {
            throw new ConflictException("Office already reserved for selected dates");
        }

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByEmail(email).orElseThrow();
        Office office = officeRepo.findById(officeId).orElseThrow();

        long days = ChronoUnit.DAYS.between(start, end);
        double totalPrice = days * office.getDailyPrice();

        Reservation r = new Reservation();
        r.setOffice(office);
        r.setUser(user);
        r.setStartDate(start);
        r.setEndDate(end);
        r.setTotalDays((int) days);
        r.setTotalPrice(totalPrice);
        r.setStatus(ReservationStatus.APPROVED);

        reservationRepo.save(r);
        Reservation saved = reservationRepo.save(r);
        return saved.getId();
    }

    public List<Reservation> getAllReservations() {
    return reservationRepo.findAllWithOfficeAndUser();
}
    @Transactional
    public void deleteReservation(Long id) {
        paymentRepo.findByReservationId(id).ifPresent(paymentRepo::delete);
        reservationRepo.deleteById(id);
    }

    public java.util.List<MyReservationResponse> getMyReservations() {
        String email = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        User user = userRepo.findByEmail(email).orElseThrow();

        return reservationRepo.findByUserIdOrderByStartDateDesc(user.getId())
                .stream()
                .map(r -> new MyReservationResponse(
                        r.getId(),
                        r.getOffice().getId(),
                        r.getOffice().getName(),
                        r.getStartDate(),
                        r.getEndDate(),
                        r.getTotalDays(),
                        r.getTotalPrice(),
                        r.getStatus() == null ? null : r.getStatus().name()))
                .collect(Collectors.toList());
    }
}
