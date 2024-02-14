package com.cbzf.apis.user.rest;

import com.cbzf.apis.user.repository.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
