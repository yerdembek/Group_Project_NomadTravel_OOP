package org.example.spring_for_project.services.interfaces;

import org.example.spring_for_project.models.Tour;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface TourServiceInterface {
    List<Tour> getAllTours();

    Optional<Tour> getTourById(Long id);

    List<Tour> getToursByCategory(String category);

    List<Tour> getToursByDurationRange(Duration minDuration, Duration maxDuration);

    List<Tour> getFilteredTours(String category, BigDecimal minPrice, BigDecimal maxPrice, Duration minDuration, Duration maxDuration);

    Tour createTour(Tour tour);

    void deleteTour(Long id);

    Tour updateTour(Long id, Tour updatedTour);
}
