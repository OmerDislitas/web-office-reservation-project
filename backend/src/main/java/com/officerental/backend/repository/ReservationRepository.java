package com.officerental.backend.repository;

import com.officerental.backend.model.entity.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatus(String status);
    List<Reservation> findByUserId(Long userId);
    long countByStatus(String status);
}
