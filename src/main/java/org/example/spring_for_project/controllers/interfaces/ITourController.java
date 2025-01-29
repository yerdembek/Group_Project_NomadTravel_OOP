package org.example.spring_for_project.controllers.interfaces;

import org.example.spring_for_project.models.Tour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public interface ITourController {

    // Получить все туры
    @GetMapping
    List<Tour> getAllTours();

    // Получить тур по ID
    @GetMapping("/{id}")
    Tour getTourById(@PathVariable Long id);

    // Получить туры по категории
    @GetMapping("/filter/category")
    List<Tour> getToursByCategory(@RequestParam String category);

    // Получить туры по диапазону длительности
    @GetMapping("/filter/duration")
    List<Tour> getToursByDurationRange(
            @RequestParam String minDuration,
            @RequestParam String maxDuration
    );

    // Получить фильтрованные туры
    @GetMapping("/filter")
    List<Tour> getFilteredTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Duration minDuration,
            @RequestParam(required = false) Duration maxDuration
    );

    // Создать тур
    @PostMapping
    Tour createTour(@RequestBody Tour tour);

    // Удалить тур по ID
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTour(@PathVariable Long id);

    // Обновить тур по ID
    @PutMapping("/{id}")
    ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour updatedTour);
}
