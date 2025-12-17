package com.officerental.backend.controller;

import com.officerental.backend.model.entity.Review;
import com.officerental.backend.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // USER: create a review
    @PostMapping
    public Review create(@RequestBody CreateReviewRequest request, Principal principal) {
        return reviewService.createReview(principal.getName(), request.getOfficeId(), request.getRating(), request.getComment());
    }

    // PUBLIC: list reviews by office
    @GetMapping("/office/{officeId}")
    public List<Review> getByOffice(@PathVariable Long officeId) {
        return reviewService.getReviewsByOffice(officeId);
    }

    // ADMIN: delete a review
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    // DTO for creating reviews
    public static class CreateReviewRequest {
        private Long officeId;
        private int rating;
        private String comment;

        public Long getOfficeId() { return officeId; }
        public void setOfficeId(Long officeId) { this.officeId = officeId; }

        public int getRating() { return rating; }
        public void setRating(int rating) { this.rating = rating; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}