package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.models.ReviewStatusUpdate;
import com.codecademy.portfolio.models.Status;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final RestaurantRepository restaurantRepository;
    private final DiningReviewRepository diningReviewRepository;

    public AdminController(RestaurantRepository restaurantRepository,
                           DiningReviewRepository diningReviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
    }

    @PutMapping("review/{id}")
    public DiningReview adminReview(@PathVariable("id") Long id, @RequestBody ReviewStatusUpdate update) {
        Optional<DiningReview> reviewOptional = this.diningReviewRepository.findById(id);

        if (reviewOptional.isPresent()) {
            DiningReview reviewed = reviewOptional.get();
            reviewed.setStatus(update.getStatus());
            return this.diningReviewRepository.save(reviewed);
        }
        return null;
    }


    @GetMapping("restaurant/{id}")
    public List<DiningReview> getRestaurantApprovedReviews(@PathVariable("id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        if (restaurantOptional.isPresent()) {
            return StreamSupport.stream(this.diningReviewRepository.findAll().spliterator(), false)
                    .filter(review -> review.getStatus() == Status.ACCEPTED && Objects.equals(review.getRestaurantId(), id))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("reviews/pending")
    public List<DiningReview> getPendingReviews() {
        return StreamSupport.stream(this.diningReviewRepository.findAll().spliterator(), false)
                .filter(review -> review.getStatus() == Status.PENDING)
                .collect(Collectors.toList());
    }
}
