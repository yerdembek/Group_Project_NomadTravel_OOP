package org.example.spring_for_project.repositories.interfaces;

import org.example.spring_for_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
