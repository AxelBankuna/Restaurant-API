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

            DiningReview submittedReview = this.diningReviewRepository.save(review);
            updateRestaurantScore(review, restaurantOptional.get());
            return submittedReview;
        } else {
            System.out.println("User or restaurant does not exist");
            return null;
        }
    }

    @GetMapping("")
    public Iterable<DiningReview> getAllReviews() {
        return this.diningReviewRepository.findAll();
    }

    private void updateRestaurantScore(DiningReview review, Restaurant restaurant) {
        Long restaurantId = restaurant.getId();
        Double averageDairy = this.diningReviewRepository.averageDairyScoreByRestaurantId(restaurantId);
        Double averagePeanut = this.diningReviewRepository.averagePeanutScoreByRestaurantId(restaurantId);
        Double averageEgg = this.diningReviewRepository.averageEggScoreByRestaurantId(restaurantId);
        Double overallAverage = (averageDairy + averageEgg + averagePeanut) / 3;

        this.restaurantRepository.setAverages(restaurantId, averagePeanut, averageEgg, averageDairy, overallAverage);
    }
}
