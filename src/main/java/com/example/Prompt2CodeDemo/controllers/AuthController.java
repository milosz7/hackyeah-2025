package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.LoginRequest;
import com.example.Prompt2CodeDemo.dto.LoginResponse;
import com.example.Prompt2CodeDemo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication-related endpoints
 * Handles user login, logout, and other authentication operations
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * Login endpoint to authenticate user with email and password
     * @param loginRequest the login credentials
     * @return LoginResponse with authentication result and user data
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
}
