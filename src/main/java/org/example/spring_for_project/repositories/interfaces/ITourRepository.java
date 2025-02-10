package org.example.spring_for_project.repositories.interfaces;

import org.example.spring_for_project.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public interface ITourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByCategory(String category);

    List<Tour> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Tour> findByDurationBetween(Duration minDuration, Duration maxDuration);

    @Query("SELECT t FROM Tour t WHERE " +
            "(:category IS NULL OR t.category = :category) AND " +
            "(:minPrice IS NULL OR t.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR t.price <= :maxPrice) AND " +
            "(:minDuration IS NULL OR t.duration >= :minDuration) AND " +
            "(:maxDuration IS NULL OR t.duration <= :maxDuration)")
    List<Tour> findFilteredTours(
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minDuration") Duration minDuration,
            @Param("maxDuration") Duration maxDuration
    );

    @Query("SELECT t FROM Tour t WHERE t.price > :minPrice")
    List<Tour> findToursByPriceAfter(@Param("minPrice") BigDecimal minPrice);

        List<Tour> findTop3ByOrderByPriceDesc();
}
