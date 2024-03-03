package com.cbzf.apis.user.rest;

import com.cbzf.apis.user.repository.UserEntity;
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

    @GetMapping("/get_users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = service.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get_unverified_suppliers")
    public ResponseEntity<List<UserEntity>> getUnverifiedSuppliers() {
        List<UserEntity> users = service.getUnverifiedSuppliers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add_user")
    public ResponseEntity<String> createUser(
            @RequestBody List<UserInputDTO> user
    ) {
        service.storeUser(user);
        return ResponseEntity.ok("New user added");
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<String> deleteUser(
            @RequestParam Integer id
    ) {
        service.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PatchMapping("/edit_user/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable(value = "id") Integer userId,
            @RequestBody Map<String, Object> updates) {
        UserEntity updatedUser = service.editUser(userId, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/log")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(
            @RequestBody Map<String, String> requestBody
    ) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        UserResponseDTO response = service.getAuthenticatedUser(email, password);
        return ResponseEntity.ok(response);
    }
}
