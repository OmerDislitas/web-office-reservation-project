package com.officerental.backend.controller;

import com.officerental.backend.model.entity.Office;
import com.officerental.backend.service.OfficeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAll();
    }

    @GetMapping("/{id}")
    public Office getOffice(@PathVariable Long id) {
        return officeService.getById(id);
    }

    @PostMapping
    public Office createOffice(@RequestBody Office office) {
        return officeService.create(office);
    }

    @PutMapping("/{id}")
    public Office updateOffice(@PathVariable Long id, @RequestBody Office office) {
        return officeService.update(id, office);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.delete(id);
    }
}
