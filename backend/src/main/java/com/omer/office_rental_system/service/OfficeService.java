package com.omer.office_rental_system.service;

import com.omer.office_rental_system.entity.*;
import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final GeoCodingService geoCodingService;
    public void createOffice(Office office) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByEmail(email).orElseThrow();

        office.setCreatedBy(user);
        office.setStatus(OfficeStatus.PENDING);

        officeRepo.save(office);
    }

    public List<Office> getApprovedOffices(String name) {
        if (name == null || name.isBlank()) {
            return officeRepo.findByStatus(OfficeStatus.APPROVED);
        }
        return officeRepo.findByStatusAndNameContainingIgnoreCase(
                OfficeStatus.APPROVED, name);
    }

    public List<Office> getPendingOffices() {
        return officeRepo.findByStatus(OfficeStatus.PENDING);
    }

    public void approveOffice(Long id) {
    Office office = officeRepo.findById(id).orElseThrow();

    double[] coords = geoCodingService.geocode(office.getAddress());
    if (coords != null) {
        office.setLatitude(coords[0]);
        office.setLongitude(coords[1]);
    }

    office.setStatus(OfficeStatus.APPROVED);
    officeRepo.save(office);
}


    public void rejectOffice(Long id) {
        Office office = officeRepo.findById(id).orElseThrow();
        office.setStatus(OfficeStatus.REJECTED);
        officeRepo.save(office);
    }
}
