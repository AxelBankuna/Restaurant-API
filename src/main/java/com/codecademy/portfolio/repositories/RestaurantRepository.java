package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
