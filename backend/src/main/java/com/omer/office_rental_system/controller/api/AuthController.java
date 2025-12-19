package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.dto.auth.LoginRequest;
import com.omer.office_rental_system.dto.auth.RegisterRequest;
import com.omer.office_rental_system.dto.auth.UserMeResponse;
import com.omer.office_rental_system.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest req,
            HttpServletRequest request,
            HttpServletResponse response) {
        authService.login(req, request, response); // session cookie burada oluşacak
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(authService.me());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}
