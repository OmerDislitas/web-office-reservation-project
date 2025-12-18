package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.repo.UserRepo;
import com.omer.office_rental_system.service.AdminManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final UserRepo userRepo;
    private final AdminManagementService adminManagementService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "Users");
        model.addAttribute("backUrl", "/admin");
        model.addAttribute("users", userRepo.findAll());
        return "admin-users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminManagementService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
