package com.officerental.backend.controller;

import com.officerental.backend.model.dto.RegisterRequest;
import com.officerental.backend.model.entity.User;
import com.officerental.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}
