package org.example.spring_for_project.repositories;

import org.example.spring_for_project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Можно добавить метод для поиска всех заказов пользователя
    List<Order> findByUserId(Long userId);
}