package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reviews")
public class DiningReviewController {

    private DiningReviewRepository diningReviewRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository) {
        this.diningReviewRepository = diningReviewRepository;
    }

    @PostMapping("/user/{id}")
    public DiningReview submitReview(User user, DiningReview review) {
        review.setUsername(user.getUsername());

        return this.diningReviewRepository.save(review);
    }
}
