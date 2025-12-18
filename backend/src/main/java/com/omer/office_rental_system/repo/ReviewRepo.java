package com.omer.office_rental_system.repo;

import com.omer.office_rental_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findByOfficeIdOrderByCreatedAtDesc(Long officeId);
}
