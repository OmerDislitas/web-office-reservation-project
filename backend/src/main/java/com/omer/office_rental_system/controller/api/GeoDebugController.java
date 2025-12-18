package com.omer.office_rental_system.controller.api;

import com.omer.office_rental_system.service.GeoCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug/geo")
@RequiredArgsConstructor
public class GeoDebugController {

    private final GeoCodingService geoCodingService;

    @GetMapping
    public Map<String, Object> test(@RequestParam String address) {
        double[] c = geoCodingService.geocode(address);

        Map<String, Object> resp = new java.util.HashMap<>();
        resp.put("address", address);

        if (c == null) {
            resp.put("result", "NO_RESULT"); // null koyma
            return resp;
        }

        resp.put("lat", c[0]);
        resp.put("lon", c[1]);
        return resp;
    }
}
