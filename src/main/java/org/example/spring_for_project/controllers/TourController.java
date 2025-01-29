package org.example.spring_for_project.controllers;

import org.example.spring_for_project.controllers.interfaces.ITourController;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.interfaces.IOrderRepository;
import org.example.spring_for_project.repositories.interfaces.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/tours")
public class TourController implements ITourController {

    @Autowired
    private ITourRepository tourRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @GetMapping
    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @GetMapping("/{id}")
    @Override
    public Tour getTourById(@PathVariable Long id) {
        return tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Тур не найден!"));
    }

    @GetMapping("/filter/category")
    @Override
    public List<Tour> getToursByCategory(@RequestParam String category) {
        return tourRepository.findByCategory(category);
    }

    @GetMapping("/filter/duration")
    @Override
    public List<Tour> getToursByDurationRange(
            @RequestParam String minDuration,
            @RequestParam String maxDuration) {
        Duration min = Duration.parse(minDuration);
        Duration max = Duration.parse(maxDuration);
        return tourRepository.findByDurationBetween(min, max);
    }

    @Override
    public List<Tour> getFilteredTours(String category, BigDecimal minPrice, BigDecimal maxPrice, Duration minDuration, Duration maxDuration) {
        return List.of();
    }

    @GetMapping("/filter")
    public List<Tour> filterTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String minDuration,
            @RequestParam(required = false) String maxDuration
    ) {
        // Преобразуем строки в объекты Duration
        Duration minDur = (minDuration != null) ? Duration.parse(minDuration) : null;
        Duration maxDur = (maxDuration != null) ? Duration.parse(maxDuration) : null;

        // Вызываем метод репозитория
        return tourRepository.findFilteredTours(category, minPrice, maxPrice, minDur, maxDur);
    }


    @PostMapping
    @Override
    public Tour createTour(@RequestBody Tour tour) {
        return tourRepository.save(tour);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteTour(@PathVariable Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тур с ID " + id + " не найден!"));

        List<Order> relatedOrders = orderRepository.findByTour_Id(id);
        if (!relatedOrders.isEmpty()) {
            return ResponseEntity.badRequest().body("Тур связан с заказами и не может быть удалён.");
        }

        tourRepository.deleteById(id);
        return ResponseEntity.ok("Тур с ID " + id + " успешно удалён.");
    }

    @PutMapping("/{id}")
    @Override
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
