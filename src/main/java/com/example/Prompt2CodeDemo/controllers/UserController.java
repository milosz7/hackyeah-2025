package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.dto.UserRequestWrapper;
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
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader("email") String email) {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, @RequestHeader("email") String email) {
        Optional<UserDto> userResult = userService.getUserById(id);
        return userResult.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequestWrapper<UserDto> request, @RequestHeader("email") String email) {
        UserDto createdUser = userService.createUser(request.getData());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestWrapper<UserDto> request, @RequestHeader("email") String email) {
        Optional<UserDto> updatedUser = userService.updateUser(id, request.getData());
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestHeader("email") String email) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok().build() 
                       : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email, @RequestHeader("email") String headerEmail) {
        Optional<UserDto> userResult = userService.getUserByEmail(email);
        return userResult.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable Long userId, @PathVariable Integer roleId, @RequestHeader("email") String email) {
        Optional<UserDto> userResult = userService.assignRoleToUser(userId, roleId);
        return userResult.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> removeRoleFromUser(@PathVariable Long userId, @PathVariable Integer roleId, @RequestHeader("email") String email) {
        Optional<UserDto> userResult = userService.removeRoleFromUser(userId, roleId);
        return userResult.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Integer roleId, @RequestHeader("email") String email) {
        List<UserDto> users = userService.getUsersByRole(roleId);
        return ResponseEntity.ok(users);
    }
}
