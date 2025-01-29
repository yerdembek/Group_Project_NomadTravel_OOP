package org.example.spring_for_project.models.interfaces;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface ITour {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    String getLocation();

    void setLocation(String location);

    Integer getMaxParticipants();

    void setMaxParticipants(Integer maxParticipants);

    Duration getDuration();

    void setDuration(Duration duration);

    String getCategory();

    void setCategory(String category);

    List<String> getImages();

    void setImages(List<String> images);
}
