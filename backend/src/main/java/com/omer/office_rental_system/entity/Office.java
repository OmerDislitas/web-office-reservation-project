package com.omer.office_rental_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offices")
@Getter
@Setter
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String address;

    private double dailyPrice;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private OfficeStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
