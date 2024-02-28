package com.cbzf.apis.user.rest;

import com.cbzf.apis.user.repository.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cbzf")
public class UserController {
    private final UserService service;
    private UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = service.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(
            @RequestBody Map<String, String> requestBody
    ) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        UserResponseDTO response = service.getAuthenticatedUser(email, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
