package com.officerental.backend.controller;

import com.officerental.backend.model.entity.Office;
import com.officerental.backend.service.OfficeService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {
    private final OfficeService officeService;

    @GetMapping("/search")
    public List<Office> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize) {
        return officeService.search(city, minPrice, maxPrice, minSize, maxSize);
    }

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public List<Office> getOffices(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize) {
        return officeService.search(city, minPrice, maxPrice, minSize, maxSize);
    }

    @GetMapping("/{id}")
    public Office getOffice(@PathVariable Long id) {
        return officeService.getById(id);
    }

    @PostMapping
    public Office createOffice(@RequestBody Office office, Principal principal) {
        return officeService.create(office, principal.getName());
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
