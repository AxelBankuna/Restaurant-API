package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class DiningReviewController {

    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository,
                                  RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/review/user/{id}")
    public DiningReview submitReview(@RequestBody DiningReview review, @PathVariable("id") Long id) {

        Optional<User> userOptional = this.userRepository.findById(id);
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(review.getRestaurantId());

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            review.setUsername(userOptional.get().getUsername());
        } else {
            System.out.println("User of restaurant does not exist");
            return null;
        }
        return this.diningReviewRepository.save(review);
    }

    @GetMapping("")
    public Iterable<DiningReview> getAllReviews() {
        return this.diningReviewRepository.findAll();
    }
}
