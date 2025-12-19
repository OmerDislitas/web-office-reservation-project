package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.service.AdminManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.omer.office_rental_system.service.OfficeService;
@Controller
@RequestMapping("/admin/offices")
@RequiredArgsConstructor
public class AdminOfficesController {

    private final OfficeRepo officeRepo;
    private final AdminManagementService adminManagementService;
    private final OfficeService officeService;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "Offices");
        model.addAttribute("backUrl", "/admin");
        model.addAttribute("offices", officeRepo.findAll());
        model.addAttribute("title", "Pending Offices");
        model.addAttribute("backUrl", "/admin");

        return "admin-offices";
    }
@PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        officeService.approveOffice(id);
        return "redirect:/admin/offices";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminManagementService.deleteOffice(id);
        return "redirect:/admin/offices";
    }
}
