package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.repositories.RestaurantRepository;
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
                restaurant -> newRestaurant.equals(restaurant)
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
    public Restaurant getRestaurant(@PathVariable("id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        return restaurantOptional.orElse(null);
    }

    @GetMapping("search")
    public List<Restaurant> getRestaurantsByZipcodeAndAllergy(
            @RequestParam(required = false) Integer zipcode,
            @RequestParam(required = false) String allergy) {

        if (Objects.nonNull(zipcode)) {
            System.out.println("searching for all restaurants by zipcode");
            return this.restaurantRepository.findAllByZipcode(zipcode);
        }

        if (Objects.nonNull(allergy)) {

        }
        return null;
        // TODO: 2023/01/21 complete functionality with allergies
    }
}
