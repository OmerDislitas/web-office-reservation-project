package com.omer.office_rental_system.controller.admin;

import com.omer.office_rental_system.entity.User;
import com.omer.office_rental_system.repo.UserRepo;
import com.omer.office_rental_system.service.AdminManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final UserRepo userRepo;
    private final AdminManagementService adminManagementService;

    @GetMapping
    public String list(Model model, Principal principal) {
        model.addAttribute("title", "Users");
        model.addAttribute("backUrl", "/admin");

        String myEmail = principal.getName();

        List<User> users = userRepo.findAll()
                .stream()
                .filter(u -> !u.getEmail().equalsIgnoreCase(myEmail))
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "admin-users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminManagementService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
