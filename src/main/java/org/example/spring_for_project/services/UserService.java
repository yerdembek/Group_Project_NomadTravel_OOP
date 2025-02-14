package org.example.spring_for_project.services;

import org.example.spring_for_project.models.User;
import org.example.spring_for_project.repositories.interfaces.IUserRepository;
import org.example.spring_for_project.services.interfaces.UserServiceInterface;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService implements UserServiceInterface {
    private final IUserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user with this email address was found"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("Email empty");
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("Email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("Email is already in use");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        log.info("The user is saved in the database with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }
}
