package org.example.spring_for_project.repositories;

import org.example.spring_for_project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Найти все заказы пользователя по его ID
    List<Order> findByUserId(Long userId);

    // Найти все заказы, связанные с туром по ID
    List<Order> findByTour_Id(Long tourId);
}
