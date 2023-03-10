package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DiningReview> submitReview(@RequestBody DiningReview review, @PathVariable("id") Long id) {

        Optional<User> userOptional = this.userRepository.findById(id);
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(review.getRestaurantId());

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            review.setUsername(userOptional.get().getUsername());

            DiningReview submittedReview = this.diningReviewRepository.save(review);
            return new ResponseEntity<>(submittedReview, HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            if (userOptional.isEmpty())
                headers.add("X-Error-Message", "Error: User does not exist");
            if (restaurantOptional.isEmpty())
                headers.add("X-Error-Message", "Error: Restaurant does not exist");
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<Iterable<DiningReview>> getAllReviews() {
        try{
            return new ResponseEntity<>(this.diningReviewRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Error-Message", "Error: " + e.getMessage());
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
    }
}
