package com.omer.office_rental_system.service;

import com.omer.office_rental_system.dto.geo.NominatimResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.UriComponentsBuilder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeoCodingService {

    private final RestClient restClient;

    public GeoCodingService(RestClient restClient) {
        this.restClient = restClient;
    }

    public double[] geocode(String address) {
        try {
            // 🔧 normalize: virgül/çoklu boşluk temizle + TR ekleyelim
            String normalized = address
                    .replace(",", " ")
                    .replaceAll("\\s+", " ")
                    .trim();

            // TR sinyali (isteğe bağlı ama işe yarıyor)
            String query = normalized + " Turkey";

            String uri = UriComponentsBuilder
                    .fromUriString("https://nominatim.openstreetmap.org/search")
                    .queryParam("format", "json")
                    .queryParam("limit", "1")
                    .queryParam("q", query)
                    .build()
                    .toUriString();

            NominatimResult[] results = restClient.get()
                    .uri(uri)
                    .header("User-Agent", "office-rental-system/1.0 (admin@office.com)")
                    .header("Accept", "application/json")
                    .retrieve()
                    .body(NominatimResult[].class);

            if (results == null || results.length == 0)
                return null;

            return new double[] {
                    Double.parseDouble(results[0].getLat()),
                    Double.parseDouble(results[0].getLon())
            };

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
