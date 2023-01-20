package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.DiningReview;
import org.springframework.data.repository.CrudRepository;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
}
