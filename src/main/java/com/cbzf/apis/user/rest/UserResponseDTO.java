package com.cbzf.apis.user.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
    private SupplierResponseDTO supplier;
}
