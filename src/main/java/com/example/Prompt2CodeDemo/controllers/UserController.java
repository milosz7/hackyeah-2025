package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        Optional<UserDto> updatedUser = userService.updateUser(id, userDto);
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok().build() 
                       : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        Optional<UserDto> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable Long userId, @PathVariable Integer roleId) {
        Optional<UserDto> user = userService.assignRoleToUser(userId, roleId);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> removeRoleFromUser(@PathVariable Long userId, @PathVariable Integer roleId) {
        Optional<UserDto> user = userService.removeRoleFromUser(userId, roleId);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Integer roleId) {
        List<UserDto> users = userService.getUsersByRole(roleId);
        return ResponseEntity.ok(users);
    }
}
