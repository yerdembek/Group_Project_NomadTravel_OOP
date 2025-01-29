package org.example.spring_for_project.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.spring_for_project.models.Tour;
import org.example.spring_for_project.repositories.custom.CustomTourRepository;

import java.util.List;

public class TourRepository implements CustomTourRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> findCustomTours() {
        String query = "SELECT t FROM Tour t WHERE t.price > 100";
        TypedQuery<Tour> typedQuery = entityManager.createQuery(query, Tour.class);
        return typedQuery.getResultList();
    }
}
