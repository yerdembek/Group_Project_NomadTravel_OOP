package org.example.spring_for_project.services;

import lombok.RequiredArgsConstructor;
import org.example.spring_for_project.enums.PaymentStatus;
import org.example.spring_for_project.models.Order;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.models.User;
import org.example.spring_for_project.repositories.interfaces.IOrderRepository;
import org.example.spring_for_project.repositories.interfaces.ITourRepository;
import org.example.spring_for_project.services.interfaces.TourServiceInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourService implements TourServiceInterface {
    private final ITourRepository tourRepository;
    private final IOrderRepository orderRepository;
    private final SessionService sessionService;

//    public TourService(ITourRepository tourRepository, IOrderRepository orderRepository, SessionService sessionService) {
//        this.tourRepository = tourRepository;
//        this.orderRepository = orderRepository;
//        this.sessionService = sessionService;
//    }

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

    public void bookTour(Long tourId, int seats) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour with ID " + tourId + " not found"));

        if (tour.getMaxParticipants() < seats) {
            throw new IllegalStateException("Not enough reservations");
        }

        tour.setMaxParticipants(tour.getMaxParticipants() - seats);
        tourRepository.save(tour);

        User currentUser = sessionService.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("User not authorized");
        }
        Order order = new Order();
        order.setUser(currentUser);
        order.setTour(tour);
        order.setSeatsBooked(seats);
        order.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(seats)));
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.PENDING);

        orderRepository.save(order);
    }

    public List<Tour> getPopularTours() {
        return tourRepository.findTop3ByOrderByPriceDesc();
    }
}
