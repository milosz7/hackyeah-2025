package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.LoginRequest;
import com.example.Prompt2CodeDemo.dto.LoginResponse;

/**
 * Service interface for authentication-related operations
 * Handles user login and other authentication logic
 */
public interface AuthService {
    
    /**
     * Authenticates a user with email and password
     * @param loginRequest the login credentials
     * @return LoginResponse containing authentication result and user data
     */
    LoginResponse login(LoginRequest loginRequest);
}
