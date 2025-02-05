package org.example.spring_for_project.controllers;

import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.interfaces.ITourRepository;
import org.example.spring_for_project.services.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TourViewController {

    private final ITourRepository tourRepository;

    public TourViewController(ITourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @GetMapping("/tours")
    public String showTours(Model model) {
        List<Tour> tours = tourRepository.findAll();
        model.addAttribute("tours", tours);
        return "tour-list"; // Ссылается на "tour-list.html"
    }

    @GetMapping("/tours/{id}")
    public String showTourDetails(@PathVariable Long id, Model model) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
        model.addAttribute("tour", tour);
        return "tour-details";
    }

    @GetMapping("/catalogue")
    public String showTourCatalogue(Model model) {
        List<Tour> tours = TourService.getAllTours();
        model.addAttribute("tours", tours);
        return "tour-list"; // Название шаблона Thymeleaf
    }

}