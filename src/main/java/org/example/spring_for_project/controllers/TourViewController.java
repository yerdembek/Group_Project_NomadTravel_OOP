package org.example.spring_for_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.services.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TourViewController {

    private final TourService tourService;

    @GetMapping("/tours")
    public String getAllTours(Model model) {
        List<Tour> tours = tourService.getAllTours();
        model.addAttribute("tours", tours);
        return "tours";
    }

    @GetMapping("/tours/{id}")
    public String getTourDetails(@PathVariable Long id, Model model) {
        tourService.getTourById(id).ifPresentOrElse(
                tour -> model.addAttribute("tour", tour),
                () -> model.addAttribute("error", "Tour not found!")
        );
        return "tour-details";
    }

    @GetMapping("/tours/{id}/book")
    public String getBookingPage(@PathVariable Long id, Model model) {
        return tourService.getTourById(id)
                .map(tour -> {
                    model.addAttribute("tour", tour);
                    return "tour-booking";
                })
                .orElse("redirect:/tours?error=Tour+not+found");
    }


}
