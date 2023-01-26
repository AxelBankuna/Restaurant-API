package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.models.ReviewStatusUpdate;
import com.codecademy.portfolio.models.Status;
import com.codecademy.portfolio.repositories.DiningReviewRepository;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public ResponseEntity<DiningReview> adminReview(@PathVariable("id") Long id,
                                                   @RequestBody ReviewStatusUpdate update) {
        Optional<DiningReview> reviewOptional = this.diningReviewRepository.findById(id);

        if (reviewOptional.isPresent()) {
            DiningReview reviewed = reviewOptional.get();
            reviewed.setStatus(update.getStatus());

            Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(
                    reviewed.getRestaurantId()
            );

            if (restaurantOptional.isPresent()) {
                try {
                    DiningReview updatedReview = this.diningReviewRepository.save(reviewed);
                    updateRestaurantScore(restaurantOptional.get());
                    return new ResponseEntity<>(updatedReview, HttpStatus.OK);
                } catch (Exception e) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Error-Message",
                            "Error: failed to review dining-review with id: " + id + ". "
                                    + e.getMessage());
                    return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
                }
            }

        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Message", "Error: failed to find review with id: " + id);
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }


    @GetMapping("restaurant/{id}")
    public ResponseEntity<List<DiningReview>> getRestaurantApprovedReviews(@PathVariable("id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        if (restaurantOptional.isPresent()) {
            List<DiningReview> approvedReviews =
                    StreamSupport.stream(this.diningReviewRepository.findAll().spliterator(), false)
                    .filter(review -> review.getStatus() == Status.ACCEPTED && Objects.equals(review.getRestaurantId(), id))
                    .toList();
            return new ResponseEntity<>(approvedReviews, HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Message",
                "Error: Unable to get approved reviews for restaurant with id: " + id);
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @GetMapping("reviews/pending")
    public ResponseEntity<List<DiningReview>> getPendingReviews() {
        try {
            List<DiningReview> pendingReviews = StreamSupport
                    .stream(this.diningReviewRepository.findAll().spliterator(), false)
                    .filter(review -> review.getStatus() == Status.PENDING)
                    .toList();
            return new ResponseEntity<>(pendingReviews, HttpStatus.OK);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Error-Message",
                    "Error: Unable to get list of pending reviews: " + e.getMessage());
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
    }

    private void updateRestaurantScore(Restaurant restaurant) {
        Long restaurantId = restaurant.getId();
        Double averageDairy = this.diningReviewRepository.averageDairyScoreByRestaurantId(restaurantId);
        Double averagePeanut = this.diningReviewRepository.averagePeanutScoreByRestaurantId(restaurantId);
        Double averageEgg = this.diningReviewRepository.averageEggScoreByRestaurantId(restaurantId);
        Double overallAverage = (averageDairy + averageEgg + averagePeanut) / 3;

        this.restaurantRepository.setAverages(restaurantId, averagePeanut, averageEgg, averageDairy, overallAverage);
    }
}
