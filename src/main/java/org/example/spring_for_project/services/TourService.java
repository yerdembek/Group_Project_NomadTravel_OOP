package org.example.spring_for_project.services;

import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.interfaces.ITourRepository;
import org.example.spring_for_project.services.interfaces.TourServiceInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class TourService implements TourServiceInterface {
    private final ITourRepository tourRepository;

    public TourService(ITourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @Override
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    @Override
    public List<Tour> getToursByCategory(String category) {
        return tourRepository.findByCategory(category);
    }

    @Override
    public List<Tour> getToursByDurationRange(Duration minDuration, Duration maxDuration) {
        return tourRepository.findByDurationBetween(minDuration, maxDuration);
    }

    @Override
    public List<Tour> getFilteredTours(String category, BigDecimal minPrice, BigDecimal maxPrice, Duration minDuration, Duration maxDuration) {
        return tourRepository.findFilteredTours(category, minPrice, maxPrice, minDuration, maxDuration);
    }

    @Override
    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new IllegalArgumentException("Tour with ID " + id + " does not exist");
        }
        tourRepository.deleteById(id);
    }

    @Override
    public Tour updateTour(Long id, Tour updatedTour) {
        return tourRepository.findById(id)
                .map(existingTour -> {
                    existingTour.setName(updatedTour.getName());
                    existingTour.setDescription(updatedTour.getDescription());
                    existingTour.setPrice(updatedTour.getPrice());
                    existingTour.setStartDate(updatedTour.getStartDate());
                    existingTour.setEndDate(updatedTour.getEndDate());
                    existingTour.setLocation(updatedTour.getLocation());
                    existingTour.setMaxParticipants(updatedTour.getMaxParticipants());
                    existingTour.setDuration(updatedTour.getDuration());
                    existingTour.setCategory(updatedTour.getCategory());
                    existingTour.setImages(updatedTour.getImages());
                    return tourRepository.save(existingTour);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tour with ID " + id + " not found"));
    }

    public Tour bookTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tour with ID " + id + " not found"));

        if (tour.getMaxParticipants() <= 0) {
            throw new IllegalStateException("No available spots for this tour.");
        }

        tour.setMaxParticipants(tour.getMaxParticipants() - 1);

        return tourRepository.save(tour);
    }
}
