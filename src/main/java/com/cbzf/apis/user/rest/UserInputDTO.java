package com.cbzf.apis.user.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserInputDTO {

    private Integer idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private LocalDateTime dateAdded;
}
