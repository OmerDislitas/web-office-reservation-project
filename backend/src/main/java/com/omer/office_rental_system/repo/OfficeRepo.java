package com.omer.office_rental_system.repo;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.entity.OfficeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.List;

public interface OfficeRepo extends JpaRepository<Office, Long> {
    List<Office> findByCreatedById(Long userId);

    List<Office> findByStatus(OfficeStatus status);

    List<Office> findByStatusAndNameContainingIgnoreCase(
            OfficeStatus status, String name);
}
