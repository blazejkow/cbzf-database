package com.cbzf.apis.user.rest;

import com.cbzf.apis.user.repository.UserRepository;
import com.cbzf.apis.user.repository.UserEntity;
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
