package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderViewController {
    private final OrderService orderService;

    @GetMapping
    public String showOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String showOrderDetails(@PathVariable Long id, Model model) {
        System.out.println("Fetching order with ID: " + id);
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        System.out.println("Order fetched: " + order);
        model.addAttribute("order", order);
        return "order-details";
    }

}

