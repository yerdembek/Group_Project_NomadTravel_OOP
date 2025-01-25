package org.example.spring_for_project.controllers;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.OrderRepository;
import org.example.spring_for_project.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private OrderRepository orderRepository;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

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

    @GetMapping("/filter")
    public List<Tour> getFilteredTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Duration minDuration,
            @RequestParam(required = false) Duration maxDuration) {
        return tourRepository.findFilteredTours(category, minPrice, maxPrice, minDuration, maxDuration);
    }

    // Добавить новый тур
    @PostMapping
    public Tour createTour(@RequestBody Tour tour) {
        return tourRepository.save(tour);
    }

    // Удалить тур
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable Long id) {
        // Проверяем, существует ли тур
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тур с ID " + id + " не найден!"));

        // Проверяем наличие связанных заказов
        List<Order> relatedOrders = orderRepository.findByTour_Id(id);
        if (!relatedOrders.isEmpty()) {
            return ResponseEntity.badRequest().body("Тур связан с заказами и не может быть удалён.");
        }

        // Если зависимостей нет, удаляем тур
        tourRepository.deleteById(id);
        return ResponseEntity.ok("Тур с ID " + id + " успешно удалён.");
    }



    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour updatedTour) {
        return tourRepository.findById(id)
                .map(existingTour -> {
                    existingTour.setName(updatedTour.getName());
                    existingTour.setDescription(updatedTour.getDescription());
                    existingTour.setPrice(updatedTour.getPrice());
                    existingTour.setStartDate(updatedTour.getStartDate());
                    existingTour.setEndDate(updatedTour.getEndDate());
                    existingTour.setLocation(updatedTour.getLocation());
                    existingTour.setMaxParticipants(updatedTour.getMaxParticipants());
                    if (updatedTour.getDuration() != null) {
                        existingTour.setDuration(updatedTour.getDuration());
                    }
                    existingTour.setCategory(updatedTour.getCategory());
                    existingTour.setImages(updatedTour.getImages());
                    tourRepository.save(existingTour);
                    return ResponseEntity.ok(existingTour);
                })
                .orElse(ResponseEntity.notFound().build());

    }
}

