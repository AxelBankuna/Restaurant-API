package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.Restaurant;
import com.codecademy.portfolio.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        if (restaurantList.stream().filter(r -> r.get))
    }
}
