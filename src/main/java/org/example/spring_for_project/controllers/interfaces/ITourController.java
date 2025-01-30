package org.example.spring_for_project.controllers.interfaces;

import org.example.spring_for_project.models.Tour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public interface ITourController {

    @GetMapping
    List<Tour> getAllTours();

    @GetMapping("/{id}")
    Tour getTourById(@PathVariable Long id);

    @GetMapping("/filter/category")
    List<Tour> getToursByCategory(@RequestParam String category);

    @GetMapping("/filter/duration")
    List<Tour> getToursByDurationRange(
            @RequestParam String minDuration,
            @RequestParam String maxDuration
    );

    @GetMapping("/filter")
    List<Tour> getFilteredTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Duration minDuration,
            @RequestParam(required = false) Duration maxDuration
    );

    @PostMapping
    Tour createTour(@RequestBody Tour tour);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTour(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour updatedTour);
}
