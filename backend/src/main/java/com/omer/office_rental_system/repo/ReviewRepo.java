package com.omer.office_rental_system.repo;

import com.omer.office_rental_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    void deleteByOfficeId(Long officeId);

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);

    List<Review> findByOfficeIdOrderByCreatedAtDesc(Long officeId);
}
