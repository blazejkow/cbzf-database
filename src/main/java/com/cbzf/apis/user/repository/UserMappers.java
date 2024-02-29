package com.cbzf.apis.user.repository;

import com.cbzf.apis.user.rest.UserInputDTO;

import java.util.List;

public class UserMappers {

    public List<UserEntity> provideEntityFromDto(List<UserInputDTO> input) {

        return input.stream().map(dto -> {
            UserEntity entity = new UserEntity();
            entity.setIdUser(dto.getIdUser());
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setRole(dto.getRole());
            return entity;
        }).toList();
    }
}
