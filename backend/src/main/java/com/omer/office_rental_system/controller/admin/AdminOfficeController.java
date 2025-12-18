package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/offices")
@RequiredArgsConstructor
public class AdminOfficeController {

    private final OfficeService officeService;

    @GetMapping("/pending")
    public String pending(Model model) {
        List<Office> offices = officeService.getPendingOffices();
        model.addAttribute("offices", offices);
        return "admin-offices";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        officeService.approveOffice(id);
        return "redirect:/admin/offices/pending";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        officeService.rejectOffice(id);
        return "redirect:/admin/offices/pending";
    }
}
