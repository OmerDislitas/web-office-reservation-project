package com.officerental.backend.controller;

import com.officerental.backend.model.entity.Office;
import com.officerental.backend.service.OfficeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/offices")
public class AdminOfficeController {

    private final OfficeService officeService;

    public AdminOfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public String officesPage(Model model) {
        List<Office> offices = officeService.getAll();
        model.addAttribute("offices", offices);
        return "admin-offices";
    }

    @PostMapping
    public String createOffice(
            @RequestParam String title,
            @RequestParam String city,
            @RequestParam String address,
            @RequestParam BigDecimal pricePerMonth,
            @RequestParam Integer sizeInSqm) {
        
        Office office = new Office();
        office.setTitle(title);
        office.setCity(city);
        office.setAddress(address);
        office.setPricePerMonth(pricePerMonth);
        office.setSizeInSqm(sizeInSqm);
        office.setAvailable(true);
        
        // In a real implementation, you would set the owner properly
        // For now, we'll let the service handle it
        
        officeService.create(office, "admin@example.com"); // Placeholder owner
        return "redirect:/admin/offices";
    }
}