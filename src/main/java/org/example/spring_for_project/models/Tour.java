package org.example.spring_for_project.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tours") // Указание имени таблицы
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500) // Описание с ограничением длины
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private String location;

    @Column
    private Integer maxParticipants;

    @Column(nullable = false)
    private Duration duration; // Длительность тура

    @Column
    private String category; // Категория тура

    @ElementCollection
    @CollectionTable(name = "tour_images", joinColumns = @JoinColumn(name = "tour_id"))
    @Column(name = "image_url") // Таблица для хранения URL изображений
    private List<String> images;

    public Tour() {

    }

    public Tour(String name, String description, BigDecimal price, LocalDate startDate, LocalDate endDate, String location, Integer maxParticipants) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
    }

    public Tour(String tourName, String category, double price, String duration, String description, String[] images, String location, int maxParticipants) {
        this.name = tourName;
        this.category = category;
        this.price = BigDecimal.valueOf(price);
        this.duration = Duration.parse(duration);
        this.description = description;
        this.images = List.of(images);
        this.location = location;
        this.maxParticipants = maxParticipants;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public java.util.List<String> getImages() {
        return images;
    }

    public void setImages(java.util.List<String> images) {
        this.images = images;
    }


    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", duration=" + duration +
                ", category='" + category + '\'' +
                ", images=" + images +
                '}';
    }

}