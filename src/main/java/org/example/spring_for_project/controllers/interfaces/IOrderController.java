package org.example.spring_for_project.controllers.interfaces;

import org.example.spring_for_project.models.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IOrderController {

    @GetMapping
    List<Order> getAllOrders();

    @GetMapping("/user/{userId}")
    List<Order> getOrdersByUserId(@PathVariable Long userId);

    @GetMapping("/tour/{tourId}")
    List<Order> getOrdersByTourId(@PathVariable Long tourId);

    @PostMapping
    ResponseEntity<Order> createOrder(@RequestBody Order order);

    @GetMapping("/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id);
}
