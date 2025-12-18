package com.omer.office_rental_system.bootstrap;


import com.omer.office_rental_system.entity.*;
import com.omer.office_rental_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepo.findByEmail("admin@office.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@office.com");
            admin.setPasswordHash(passwordEncoder.encode("Admin123!"));
            admin.setRole(Role.ADMIN);
            userRepo.save(admin);
        }
    }
}
