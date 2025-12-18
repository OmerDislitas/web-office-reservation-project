package com.omer.office_rental_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Office office;

    @ManyToOne
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    private int totalDays;
    private double totalPrice;
}

