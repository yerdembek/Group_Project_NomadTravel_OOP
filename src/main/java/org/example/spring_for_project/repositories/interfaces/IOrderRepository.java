package org.example.spring_for_project.repositories.interfaces;

import org.example.spring_for_project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByTour_Id(Long tourId);
}
