package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.Status;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController("/reviews")
public class DiningReviewController {

    private DiningReviewRepository diningReviewRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository) {
        this.diningReviewRepository = diningReviewRepository;
    }

    @PostMapping("/review/user/{id}")
    public DiningReview submitReview(User user, DiningReview review) {
        review.setUsername(user.getUsername());

        return this.diningReviewRepository.save(review);
    }

    @GetMapping("/pending")
    public List<DiningReview> getPendingReviews() {
        return StreamSupport.stream(this.diningReviewRepository.findAll().spliterator(), false)
                .filter(review -> review.getStatus() == Status.PENDING)
                .collect(Collectors.toList());
    }
}
