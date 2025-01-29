package org.example.spring_for_project.repositories.custom;

import org.example.spring_for_project.models.Tour;
import java.util.List;

public interface CustomTourRepository {
    List<Tour> findCustomTours();
}
