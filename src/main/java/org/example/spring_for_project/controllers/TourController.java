package org.example.spring_for_project.controllers;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.interfaces.ITourRepository;
import org.example.spring_for_project.services.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter/category")
    public List<Tour> getToursByCategory(@RequestParam String category) {
        return tourService.getToursByCategory(category);
    }

    @GetMapping("/filter/duration")
    public List<Tour> getToursByDurationRange(
            @RequestParam String minDuration,
            @RequestParam String maxDuration
    ) {
        Duration min = Duration.parse(minDuration);
        Duration max = Duration.parse(maxDuration);
        return tourService.getToursByDurationRange(min, max);
    }

    @GetMapping("/filter")
    public List<Tour> getFilteredTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String minDuration,
            @RequestParam(required = false) String maxDuration
    ) {
        Duration min = (minDuration != null) ? Duration.parse(minDuration) : null;
        Duration max = (maxDuration != null) ? Duration.parse(maxDuration) : null;
        return tourService.getFilteredTours(category, minPrice, maxPrice, min, max);
    }



    @PostMapping
    public Tour createTour(@RequestBody Tour tour) {
        return tourService.createTour(tour);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable Long id) {
        try {
            tourService.deleteTour(id);
            return ResponseEntity.ok("Tour with ID " + id + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour updatedTour) {
        try {
            Tour tour = tourService.updateTour(id, updatedTour);
            return ResponseEntity.ok(tour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookTour(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int seats) {
        try {
            tourService.bookTour(id, seats);
            return ResponseEntity.ok("Тур успешно забронирован!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/tours/{id}/book")
    public String bookTour(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int seats,
            RedirectAttributes redirectAttributes) {
        try {
            tourService.bookTour(id, seats);

            redirectAttributes.addFlashAttribute("successMessage", "Тур успешно забронирован!");
            return "redirect:/booking-success";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/tours";
        }
    }



}
