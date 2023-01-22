package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping("user/{username}")
    public User updateUser(@PathVariable("username") String username, @RequestBody User userUpdate) {
        Optional<User> userToUpdateOptional = this.userRepository.findByUsernameIgnoreCase(username);

        if (userToUpdateOptional.isPresent()) {
            User updatedUser = updateUser(userToUpdateOptional.get(), userUpdate);
            return this.userRepository.save(updatedUser);
        }
        return null;
    }

    @GetMapping("user/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        Optional<User> userOptional = this.userRepository.findByUsernameIgnoreCase(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    private User updateUser(User databaseUser, User updates) {
        databaseUser.setCity(updates.getCity());
        databaseUser.setState(updates.getState());
        databaseUser.setZipcode(updates.getZipcode());
        databaseUser.setDairyInterest(updates.getDairyInterest());
        databaseUser.setEggInterest(updates.getEggInterest());
        databaseUser.setPeanutInterest(updates.getPeanutInterest());
        return databaseUser;
    }
}
