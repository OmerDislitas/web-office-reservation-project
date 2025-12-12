package com.officerental.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin")
    public String adminHome() {
        return "admin-home"; // templates/admin-home.html dosyasını arar
    }
}
