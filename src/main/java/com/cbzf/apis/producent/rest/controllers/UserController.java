package com.cbzf.apis.producent.rest.controllers;

import com.cbzf.apis.producent.repository.entities.UserEntity;
import com.cbzf.apis.producent.rest.services.UserService;
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
    public ResponseEntity<List<UserEntity>> getProducents() {
        List<UserEntity> users = service.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
