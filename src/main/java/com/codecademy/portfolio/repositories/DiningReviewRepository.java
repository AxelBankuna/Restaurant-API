package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.DiningReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    @Query("SELECT COUNT(*) FROM DiningReview WHERE peanutScore IS NOT NULL")
    Long countByPeanutScoreIsNotNull();
    @Query("SELECT COUNT(*) FROM DiningReview WHERE eggScore IS NOT NULL")
    Long countByEggScoreIsNotNull();
    @Query("SELECT COUNT(*) FROM DiningReview WHERE dairyScore IS NOT NULL")
    Long countByDairyScoreIsNotNull();
    @Query("SELECT AVG(peanutScore) FROM DiningReview WHERE peanutScore IS NOT NULL AND restaurantId = :restaurantId")
    Double averagePeanutScoreByRestaurantId(@Param("restaurantId") Long restaurantId);
    @Query("SELECT AVG(eggScore) FROM DiningReview WHERE eggScore IS NOT NULL AND restaurantId = :restaurantId")
    Double averageEggScoreByRestaurantId(@Param("restaurantId") Long restaurantId);
    @Query("SELECT AVG(dairyScore) FROM DiningReview WHERE dairyScore IS NOT NULL AND restaurantId = :restaurantId")
    double averageDairyScoreByRestaurantId(@Param("restaurantId") Long restaurantId);
}
