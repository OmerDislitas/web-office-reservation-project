package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.entity.Office;
import com.omer.office_rental_system.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @PostMapping
    public void create(@RequestBody Office office) {
        officeService.createOffice(office);
    }

    @GetMapping
    public List<Office> list(
            @RequestParam(required = false) String name) {
        return officeService.getApprovedOffices(name);
    }
}

