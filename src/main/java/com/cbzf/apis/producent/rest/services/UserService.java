package com.cbzf.apis.producent.rest.services;

import com.cbzf.apis.producent.repository.UserRepository;
import com.cbzf.apis.producent.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserEntity> getUsers() {
        return repository.findAll();
    }
}
