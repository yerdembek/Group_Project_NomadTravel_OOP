package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.repositories.interfaces.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use.");
        }

        if (user.getName() == null || user.getName().isEmpty() || user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Name and Email are required.");
        }

        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
