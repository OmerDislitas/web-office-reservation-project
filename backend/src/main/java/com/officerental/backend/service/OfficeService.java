package com.officerental.backend.service;

import com.officerental.backend.model.entity.Office;
import com.officerental.backend.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> getAll() {
        return officeRepository.findAll();
    }

    public Office getById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));
    }

    public Office create(Office office) {
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
}

