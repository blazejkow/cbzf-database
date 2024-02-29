package com.cbzf.apis.user.rest;

import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierEntity;
import com.cbzf.apis.user.repository.UserMappers;
import com.cbzf.apis.user.repository.UserRepository;
import com.cbzf.apis.user.repository.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final EntityManager entityManager;
    private final JWTService jwtService;
    private final UserMappers mappers = new UserMappers();

    public List<UserEntity> getUsers() {
        return repository.findAll();
    }

    public void storeUser(List<UserInputDTO> input) {

        List<UserEntity> userEntityList = mappers.provideEntityFromDto(input);

        for (UserEntity userEntity : userEntityList) {
            // Check if the role of the user is 'dostawca'
            if ("Dostawca".equals(userEntity.getRole())) {
                TemporarySupplierEntity tempSupplier = new TemporarySupplierEntity();
                tempSupplier.setUser(userEntity);// Set the user reference
                userEntity.setTemporarySupplier(tempSupplier); // Set the temporary supplier reference
            }
        }

        repository.saveAll(userEntityList);
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
        if ("dostawca".equalsIgnoreCase(user.getRole())) {
            SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
            supplierDTO.setId(user.getIdUser());
            supplierDTO.setIsApproved(user.getIsApproved());
            response.setSupplier(supplierDTO);
        } else {
            response.setSupplier(new SupplierResponseDTO());
        }

        return response;
    }

    public void deleteUserById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("uUser does not exist");
        }
        repository.deleteById(id);
    }

    public UserEntity editUser(Integer id, Map<String, Object> updates) {

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found for this id: " + id));

        updates.forEach((key, value) -> {
            // Use reflection to update only the fields that are present in the request body
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null && !key.equals("idUser")) { // Ignore updates to the ID
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });
        return repository.save(user);
    }

    private UserEntity authenticateUser(String email, String password) {
        UserEntity user = findUserByEmail(email);
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
