package org.example.spring_for_project.services.interfaces;

import org.example.spring_for_project.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User registerUser(User user);

    void deleteUser(Long id);
}
