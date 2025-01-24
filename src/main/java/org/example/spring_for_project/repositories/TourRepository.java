package org.example.spring_for_project.repositories;

import org.example.spring_for_project.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}
