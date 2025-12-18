package com.omer.office_rental_system.repo;

import com.omer.office_rental_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findByOfficeId(Long officeId);

    List<Reservation> findByUserId(Long userId);

    @Query("""
                select r from Reservation r
                where r.office.id = :officeId
                and r.startDate <= :endDate
                and r.endDate >= :startDate
            """)
    List<Reservation> findConflictingReservations(
            Long officeId,
            LocalDate startDate,
            LocalDate endDate);
}
