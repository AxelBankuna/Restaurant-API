package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.DiningReview;
import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public User createUser(@RequestBody User newUser) {
        return this.userRepository.save(newUser);
    }

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PutMapping("/user/{username}")
    public User updateUser(@PathVariable("username") String username, User userUpdate) {
        Optional<User> userToUpdateOptional = this.userRepository.findByUsername(username);

        if (userToUpdateOptional.isPresent()) {
            userUpdate.setUsername(userToUpdateOptional.get().getUsername());
            return this.userRepository.save(userUpdate);
        }
        return null;
    }

    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        Optional<User> userOptional = this.userRepository.findByUsername(username.toLowerCase());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }
}
