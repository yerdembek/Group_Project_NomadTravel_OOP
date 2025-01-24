package org.example.spring_for_project.controllers;

import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    // Получить все туры
    @GetMapping
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // Получить тур по ID
    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable Long id) {
        return tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Тур не найден!"));
    }

    // Добавить новый тур
    @PostMapping
    public Tour createTour(@RequestBody Tour tour) {
        return tourRepository.save(tour);
    }

    // Удалить тур
    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourRepository.deleteById(id);
    }
}
