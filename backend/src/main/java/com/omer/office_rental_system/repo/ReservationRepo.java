package com.omer.office_rental_system.repo;

import com.omer.office_rental_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.List;
import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserIdOrderByStartDateDesc(Long userId);
    List<Reservation> findByOfficeId(Long officeId);

    List<Reservation> findByUserId(Long userId);
@Query("""
    select r from Reservation r
    join fetch r.office
    join fetch r.user
    order by r.startDate desc
""")
List<Reservation> findAllWithOfficeAndUser();
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
