package org.example.spring_for_project.models;

import jakarta.persistence.*;
import org.example.spring_for_project.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // Имя таблицы в базе данных
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Связь с пользователем
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false) // Связь с туром
    private Tour tour;

    @Column(nullable = false)
    private Integer seatsBooked; // Количество забронированных мест

    @Column(nullable = false)
    private BigDecimal totalPrice; // Общая стоимость заказа

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // Статус оплаты

    @Column(nullable = false)
    private LocalDateTime orderDate; // Дата создания заказа


    // Конструктор по умолчанию
    public Order() {}

    // Конструктор со всеми параметрами
    public Order(Long id, User user, Tour tour, Integer seatsBooked, BigDecimal totalPrice, PaymentStatus paymentStatus, LocalDateTime orderDate) {
        this.id = id;
        this.user = user;
        this.tour = tour;
        this.seatsBooked = seatsBooked;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.orderDate = orderDate;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", tour=" + tour +
                ", seatsBooked=" + seatsBooked +
                ", totalPrice=" + totalPrice +
                ", paymentStatus=" + paymentStatus +
                ", orderDate=" + orderDate +
                '}';
    }
}
