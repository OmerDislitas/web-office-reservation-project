package com.omer.office_rental_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Office office;

    @ManyToOne
    private User user;

    private int rating; // 1-5
    private String comment;

    private LocalDateTime createdAt;
}
