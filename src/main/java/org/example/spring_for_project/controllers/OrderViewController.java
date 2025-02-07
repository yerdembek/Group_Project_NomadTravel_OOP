package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.services.OrderService;
import org.example.spring_for_project.services.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderViewController {

    private final OrderService orderService;
    private final SessionService sessionService;

    @GetMapping("/my-orders")
    public String showOrders(Model model) {
        User currentUser = sessionService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/register";
        }
        List<Order> orders = orderService.getOrdersByUserId(currentUser.getId());
        model.addAttribute("orders", orders);
        return "orders";
    }
}


