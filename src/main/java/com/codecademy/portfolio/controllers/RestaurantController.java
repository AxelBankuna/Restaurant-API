package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant newRestaurant) {
        Iterable<Restaurant> allRestaurants = this.restaurantRepository.findAll();
        if (StreamSupport.stream(allRestaurants.spliterator(), false).anyMatch(
                newRestaurant::equals
        )) {
            System.out.println("Restaurant already exists.");
            return null;
        }
        return new ResponseEntity<>(this.restaurantRepository.save(newRestaurant), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(this.restaurantRepository.findAll(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        if (restaurantOptional.isPresent())
            return new ResponseEntity<>(restaurantOptional.get(), HttpStatus.OK);
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Error-Message",
                    "Error: Restaurant not found with id: " + id);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<Restaurant>> getRestaurantsByZipcodeAndAllergy(
            @RequestParam(required = false) Integer zipcode,
            @RequestParam(required = false) String allergy) {

        if (Objects.nonNull(zipcode)) {
            if (Objects.nonNull(allergy)) {
                return switch (allergy.toLowerCase()) {
                    case "dairy" -> new ResponseEntity<>(
                            this.restaurantRepository.findAllByZipcodeAndDairyAverageIsNotNull(zipcode),
                            HttpStatus.OK
                    );
                    case "peanut" -> new ResponseEntity<>(
                            this.restaurantRepository.findAllByZipcodeAndPeanutAverageIsNotNull(zipcode),
                            HttpStatus.OK
                    );
                    case "egg" -> new ResponseEntity<>(
                            this.restaurantRepository.findAllByZipcodeAndEggAverageIsNotNull(zipcode),
                            HttpStatus.OK
                    );
                    default -> new ResponseEntity<>(
                            this.restaurantRepository.findAllByZipcodeAndNonNullAverages(zipcode),
                            HttpStatus.OK
                    );
                };
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Message",
                "Error: Unable to load list based on zipcode and allergy");
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = this.restaurantRepository.findById(id);
        HttpHeaders headers = new HttpHeaders();
        if (restaurant.isPresent()) {
            return new ResponseEntity<>(restaurant.get(), headers, HttpStatus.OK);
        } else {
            headers.add("X-Error-Message", "Error: Restaurant not found with id: " + id);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
    }
}
