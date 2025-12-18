package com.omer.office_rental_system.service;

import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.repo.PaymentRepo;
import com.omer.office_rental_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminStatsService {

    private final PaymentRepo paymentRepo;
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;

    public double totalRevenue() {
        return paymentRepo.sumTotalAmount();
    }

    public long totalOffices() {
        return officeRepo.count();
    }

    public long totalUsers() {
        return userRepo.count();
    }
}
