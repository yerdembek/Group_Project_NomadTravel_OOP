package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.example.spring_for_project.services.SessionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            log.info("Attempting to register a user with email: {}", user.getEmail());

            user.setCreatedAt(LocalDateTime.now());

            User savedUser = userService.registerUser(user);

            sessionService.setCurrentUser(savedUser);

            log.info("User successfully registered with ID: {}", savedUser.getId());
            return ResponseEntity.ok().body(savedUser);

        } catch (IllegalArgumentException e) {
            log.error("Error during user registration: {}", e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            log.error("Unexpected mistake: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("{\"error\": \"Unexpected server error\"}");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        sessionService.logout();
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = sessionService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(currentUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
