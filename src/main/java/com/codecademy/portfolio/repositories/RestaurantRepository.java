package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAllByZipcode(Integer zipcode);
}
