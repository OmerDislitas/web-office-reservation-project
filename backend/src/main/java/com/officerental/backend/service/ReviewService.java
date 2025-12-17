package com.officerental.backend.service;

import com.officerental.backend.model.entity.Review;
import com.officerental.backend.repository.ReviewRepository;
import com.officerental.backend.repository.UserRepository;
import com.officerental.backend.repository.OfficeRepository;
import com.officerental.backend.model.entity.User;
import com.officerental.backend.model.entity.Office;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, OfficeRepository officeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
    }

    public Review createReview(String userEmail, Long officeId, int rating, String comment) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        Review review = new Review();
        review.setUser(user);
        review.setOffice(office);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByOffice(Long officeId) {
        return reviewRepository.findByOfficeId(officeId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}