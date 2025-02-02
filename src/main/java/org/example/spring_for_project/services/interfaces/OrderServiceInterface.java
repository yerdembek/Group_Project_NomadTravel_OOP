package org.example.spring_for_project.services.interfaces;

import org.example.spring_for_project.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    List<Order> getOrdersByUserId(Long userId);

    List<Order> getOrdersByTourId(Long tourId);

    Order createOrder(Order order);

    void deleteOrder(Long id);
}
