package com.codecademy.portfolio.repositories;

import com.codecademy.portfolio.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
