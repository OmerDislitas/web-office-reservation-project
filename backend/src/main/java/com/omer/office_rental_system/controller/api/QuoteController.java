package com.omer.office_rental_system.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class QuoteController {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://zenquotes.io")
            .defaultHeader(HttpHeaders.USER_AGENT, "office-rental-system/1.0 (admin@office.com)")
            .build();

    @GetMapping(value = "/api/quotes/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public String random() {
        // ZenQuotes zaten JSON döndürüyor, aynen geçiriyoruz
        return restClient.get()
                .uri("/api/random")
                .retrieve()
                .body(String.class);
    }
}
