package com.codecademy.portfolio.controllers;

import com.codecademy.portfolio.models.User;
import com.codecademy.portfolio.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
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
        try {
            User createdUser = this.userRepository.save(newUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Error-Message", "Error: Failed to create User: " + e.getMessage());
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Error-Message", "Error: Failed to get Users: " + e.getMessage());
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);

        }
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
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> userOptional = this.userRepository.findByUsernameIgnoreCase(username);

        if (userOptional.isPresent())
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Error-Message", "Error: Failed to get User: " + username);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
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
