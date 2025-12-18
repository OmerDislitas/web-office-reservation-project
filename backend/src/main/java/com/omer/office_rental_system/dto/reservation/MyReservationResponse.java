package com.omer.office_rental_system.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyReservationResponse {
    private Long id;
    private Long officeId;
    private String officeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalDays;
    private double totalPrice;
    private String status;
}
