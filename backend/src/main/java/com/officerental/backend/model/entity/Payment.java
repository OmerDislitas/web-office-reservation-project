package com.officerental.backend.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime paymentDate = LocalDateTime.now();

    // PENDING, SUCCESS, FAILED
    @Column(nullable = false)
    private String status = "PENDING";

    private String externalPaymentId;

    // getters & setters
    // ...
}

