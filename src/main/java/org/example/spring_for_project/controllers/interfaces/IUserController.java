package org.example.spring_for_project.controllers.interfaces;

import org.example.spring_for_project.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUserController {

    @GetMapping
    List<User> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id);

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody User user);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
