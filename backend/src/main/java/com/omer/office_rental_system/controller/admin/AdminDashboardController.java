package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.service.AdminStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminStatsService adminStatsService;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("title", "Admin Panel");
        model.addAttribute("totalRevenue", adminStatsService.totalRevenue());
        model.addAttribute("totalUsers", adminStatsService.totalUsers());
        model.addAttribute("totalOffices", adminStatsService.totalOffices());
        model.addAttribute("backUrl", "/admin");
        return "admin-home";
    }
}
