package org.example.spring_for_project.controllers;

import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.services.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;

@Controller
public class HomeController {

    private final TourService tourService;

    public HomeController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        List<Tour> popularTours = tourService.getPopularTours();
        model.addAttribute("popularTours", popularTours);
        return "index";
    }
}
