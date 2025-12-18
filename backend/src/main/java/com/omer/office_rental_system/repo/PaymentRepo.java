package com.omer.office_rental_system.repo;
import org.springframework.data.jpa.repository.Query;
import com.omer.office_rental_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReservationId(Long reservationId);

    @Query("select coalesce(sum(p.amount), 0) from Payment p")
    double sumTotalAmount();
}
