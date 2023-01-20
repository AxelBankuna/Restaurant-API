package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public User createUser(User newUser) {
        return this.userRepository.save(newUser);
    }

    @PutMapping("/user/{username}")
    public User updateUser(@PathVariable("username") String username, User userUpdate) {
        Optional<User> userToUpdateOptional = this.userRepository.findById(username);

        if (userToUpdateOptional.isPresent()) {
            userUpdate.setUsername(userToUpdateOptional.get().getUsername());
            return this.userRepository.save(userUpdate);
        }
        return null;
    }

    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        Iterable<User> allUsers = this.userRepository.findAll();
        Optional<User> userToUpdateOptional =
                StreamSupport.stream(allUsers.spliterator(), false)
                        .filter(u -> u.getUsername().toLowerCase().equals(username.toLowerCase())).findFirst();
        if (userToUpdateOptional.isPresent()) {
            return userToUpdateOptional.get();
        }
        return null;
    }
}
