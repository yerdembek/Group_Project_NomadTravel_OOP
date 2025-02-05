package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ViewUserController {

    private final UserService userService;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // Отображение списка пользователей
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Отображение страницы регистрации
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            user.setCreatedAt(LocalDateTime.now());
            User savedUser = userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь зарегистрирован с ID: " + savedUser.getId());
            return "redirect:/users"; // Перенаправление на список пользователей
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register"; // Перенаправление обратно в случае ошибки
        }
    }
}
