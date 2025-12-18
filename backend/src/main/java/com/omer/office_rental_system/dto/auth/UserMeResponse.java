package com.omer.office_rental_system.dto.auth;

import com.omer.office_rental_system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
