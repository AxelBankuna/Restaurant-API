package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.List;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/")
    public Restaurant addRestaurant(Restaurant restaurant) {
        List<Restaurant> restaurantList = StreamSupport.stream(this.restaurantRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        if (restaurantList.size() == 0)
            return null;

        Optional<Restaurant> restaurantOptional = restaurantList.stream()
                .filter(r -> r.getName() == restaurant.getName() && r.getZipcode() == restaurant.getZipcode())
                .findFirst();

        if (restaurantOptional.isPresent()) {
            // TODO: 2023/01/21 failure when restaurant already exists
        } else
            return this.restaurantRepository.save(restaurant);

        return null;
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        if (restaurantOptional.isPresent())
            return restaurantOptional.get();

        return null;
    }

    @GetMapping("/search")
    public List<Restaurant> getRestaurantsByZipcodeAndAllergy(@RequestParam(required = false) Integer zipcode) {
        if (Objects.nonNull(zipcode)) {
            return this.restaurantRepository.findAllByZipcode(zipcode);
        }
        return null;
        // TODO: 2023/01/21 complete functionality with allergies
    }
}
