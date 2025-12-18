package com.omer.office_rental_system.dto.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimResult {
    private String lat;
    private String lon;
}
