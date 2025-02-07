package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.enums.PaymentStatus;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.services.OrderService;
import org.example.spring_for_project.services.SessionService;
import org.example.spring_for_project.services.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final TourService tourService;
    private final SessionService sessionService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/tour/{tourId}")
    public List<Order> getOrdersByTourId(@PathVariable Long tourId) {
        return orderService.getOrdersByTourId(tourId);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus newStatus) {
        try {
            orderService.updatePaymentStatus(id, newStatus);
            return ResponseEntity.ok("Payment status updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/book/{tourId}")
    public ResponseEntity<String> bookTour(@PathVariable Long tourId, @RequestParam Integer seats) {
        User currentUser = sessionService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).body("Please register before booking a tour");
        }

        Tour tour = tourService.getTourById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        if (tour.getMaxParticipants() < seats) {
            return ResponseEntity.badRequest().body("Not enough available spots");
        }

        Order order = new Order();
        order.setUser(currentUser);
        order.setTour(tour);
        order.setSeatsBooked(seats);
        order.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(seats)));
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        orderService.createOrder(order);

        return ResponseEntity.ok("Tour booked successfully for " + currentUser.getName());

    }
}
