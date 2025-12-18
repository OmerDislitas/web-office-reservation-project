package com.omer.office_rental_system.service;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.entity.Review;
import com.omer.office_rental_system.entity.User;
import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.repo.ReviewRepo;
import com.omer.office_rental_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;

    public void addReview(Long officeId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        Office office = officeRepo.findById(officeId).orElseThrow();

        Review r = new Review();
        r.setOffice(office);
        r.setUser(user);
        r.setRating(rating);
        r.setComment(comment);
        r.setCreatedAt(LocalDateTime.now());

        reviewRepo.save(r);
    }

    public List<Review> getReviews(Long officeId) {
        return reviewRepo.findByOfficeIdOrderByCreatedAtDesc(officeId);
    }
}
