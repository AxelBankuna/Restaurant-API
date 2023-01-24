package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User createdUser = this.userRepository.save(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("user/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User userUpdate) {
        Optional<User> userToUpdateOptional = this.userRepository.findByUsernameIgnoreCase(username);

        if (userToUpdateOptional.isPresent()) {
            User updatedUser = updateUser(userToUpdateOptional.get(), userUpdate);
            return new ResponseEntity<>(this.userRepository.save(updatedUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        Optional<User> userOptional = this.userRepository.findByUsernameIgnoreCase(username);
        return userOptional.orElse(null);
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
