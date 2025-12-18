package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.entity.Review;
import com.omer.office_rental_system.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<Review> list(@RequestParam Long officeId) {
        return reviewService.getReviews(officeId);
    }

    @PostMapping
    public void add(@RequestParam Long officeId,
                    @RequestParam int rating,
                    @RequestParam String comment) {

        reviewService.addReview(officeId, rating, comment);
    }
}
