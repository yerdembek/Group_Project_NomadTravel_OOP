package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.services.SessionService;
import org.example.spring_for_project.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserViewController {

    private final UserService userService;
    private final SessionService sessionService;

    public UserViewController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", null);
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", null);
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Имя шаблона страницы входа
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userService.authenticate(email, password);
            sessionService.setCurrentUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Вы успешно вошли!");
            return "redirect:/tours";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            User savedUser = userService.registerUser(user);

            sessionService.setCurrentUser(savedUser);

            redirectAttributes.addFlashAttribute("successMessage", "Вы успешно зарегистрировались!");
            return "redirect:/tours";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        User currentUser = sessionService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/register";
        }
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        sessionService.logout();
        return "redirect:/register";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно удалён!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с ID " + id + " не найден.");
        }
        return "redirect:/users";
    }

}
