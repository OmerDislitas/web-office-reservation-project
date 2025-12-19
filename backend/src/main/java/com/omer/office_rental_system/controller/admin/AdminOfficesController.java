package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.entity.OfficeStatus;
import com.omer.office_rental_system.repo.OfficeRepo;
import com.omer.office_rental_system.service.AdminManagementService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/offices")
@RequiredArgsConstructor
public class AdminOfficesController {

    private final OfficeRepo officeRepo;
    private final AdminManagementService adminManagementService;
    

    /**
     * PENDING Offices sayfası
     * URL: /admin/offices/pending
     */
    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("title", "Pending Offices");
        model.addAttribute("backUrl", "/admin");

        List<Office> pending = officeRepo.findByStatus(OfficeStatus.PENDING);
        model.addAttribute("offices", pending);

        return "admin-reservations";
    }

    /**
     * APPROVE
     * POST: /admin/offices/{id}/approve
     */
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        Office office = officeRepo.findById(id).orElseThrow();

        office.setStatus(OfficeStatus.APPROVED);

        

        officeRepo.save(office);
        return "redirect:/admin/offices/pending";
    }

    /**
     * REJECT
     * POST: /admin/offices/{id}/reject
     */
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        Office office = officeRepo.findById(id).orElseThrow();
        office.setStatus(OfficeStatus.REJECTED);
        officeRepo.save(office);
        return "redirect:/admin/offices/pending";
    }

    /**
     * ALL Offices listesi (delete var)
     * URL: /admin/offices
     */
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("title", "Offices");
        model.addAttribute("backUrl", "/admin");
        model.addAttribute("offices", officeRepo.findAll());
        return "admin-offices";
    }



    /**
     * DELETE Office
     * POST: /admin/offices/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminManagementService.deleteOffice(id);
        return "redirect:/admin/offices";
    }
}
