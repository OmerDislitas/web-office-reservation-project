package com.officerental.backend.service;

import com.officerental.backend.model.entity.Office;
import com.officerental.backend.model.entity.User;
import com.officerental.backend.repository.OfficeRepository;
import com.officerental.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public OfficeService(OfficeRepository officeRepository, UserRepository userRepository) {
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
    }

    public List<Office> getAll() {
        return officeRepository.findAll();
    }

    public Office getById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));
    }

    public Office create(Office office, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        office.setOwner(owner);
        return officeRepository.save(office);
    }

    public Office update(Long id, Office updated) {
        Office existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setCity(updated.getCity());
        existing.setAddress(updated.getAddress());
        existing.setPricePerMonth(updated.getPricePerMonth());
        existing.setSizeInSqm(updated.getSizeInSqm());
        existing.setAvailable(updated.getAvailable());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());
        existing.setOwner(updated.getOwner()); // owner kısmını ileride daha düzgün hale getireceğiz

        return officeRepository.save(existing);
    }

    public void delete(Long id) {
        officeRepository.deleteById(id);
    }

    public List<Office> search(String city, BigDecimal minPrice, BigDecimal maxPrice, Integer minSize, Integer maxSize) {
        return officeRepository.search(city, minPrice, maxPrice, minSize, maxSize);
    }
}

