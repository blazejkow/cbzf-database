package com.cbzf.apis.user.rest;

import com.cbzf.apis.user.repository.UserRepository;
import com.cbzf.apis.user.repository.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final EntityManager entityManager;
    private final JWTService jwtService;

    public List<UserEntity> getUsers() {
        return repository.findAll();
    }

    public UserResponseDTO getAuthenticatedUser(String email, String password){
        UserEntity user = authenticateUser(email, password);
        assert user != null;
        String token = jwtService.generateToken(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);
        response.setSupplier(new SupplierResponseDTO());

        return response;
    }

    private UserEntity authenticateUser(String email, String password) {
        UserEntity user = findUserByEmail(email);
        System.out.println("Blazej "+user.getIdUser());
        System.out.println("Blazej "+user.getPassword());
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private UserEntity findUserByEmail(String email) {
        try {
            TypedQuery<UserEntity> query = entityManager.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
