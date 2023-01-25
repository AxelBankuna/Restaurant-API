package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAllByZipcode(Integer zipcode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE RESTAURANTS SET peanutAverage = :peanutAverage, eggAverage = :eggAverage, dairyAverage = :dairyAverage, averageScore = :averageScore WHERE id = :id", nativeQuery = true)
    int setAverages(@Param("id") Long id, @Param("peanutAverage") Double peanutAverage, @Param("eggAverage") Double eggAverage, @Param("dairyAverage") Double dairyAverage, @Param("averageScore") Double averageScore);

    @Query("SELECT r FROM Restaurant r WHERE r.zipcode = :zipcode and (r.peanutAverage IS NOT NULL or r.eggAverage IS NOT NULL or r.dairyAverage IS NOT NULL)")
    List<Restaurant> findAllByZipcodeAndNonNullAverages(@Param("zipcode") Integer zipcode);
}
