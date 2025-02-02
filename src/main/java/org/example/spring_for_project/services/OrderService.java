package org.example.spring_for_project.services;

import org.example.spring_for_project.enums.PaymentStatus;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.repositories.interfaces.IOrderRepository;
import org.example.spring_for_project.services.interfaces.OrderServiceInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface {
    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByTourId(Long tourId) {
        return orderRepository.findByTour_Id(tourId);
    }

    @Override
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalPrice = order.getTour().getPrice().multiply(BigDecimal.valueOf(order.getSeatsBooked()));
        order.setTotalPrice(totalPrice);

        order.setPaymentStatus(PaymentStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order with ID " + id + " does not exist");
        }
        orderRepository.deleteById(id);
    }
}
