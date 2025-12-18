package com.omer.office_rental_system.service;

import com.omer.office_rental_system.dto.auth.LoginRequest;
import com.omer.office_rental_system.dto.auth.RegisterRequest;
import com.omer.office_rental_system.dto.auth.UserMeResponse;
import com.omer.office_rental_system.entity.Role;
import com.omer.office_rental_system.entity.User;
import com.omer.office_rental_system.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public void register(RegisterRequest req) {
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        userRepo.save(u);
    }

    public void login(LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // 🔥 Burası önemli: session’a kaydet
        securityContextRepository.saveContext(context, request, response);
    }

    public UserMeResponse me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userRepo.findByEmail(email).orElseThrow();
        return new UserMeResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(
                request, response, SecurityContextHolder.getContext().getAuthentication()
        );
    }
}
