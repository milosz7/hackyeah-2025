package com.example.Prompt2CodeDemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO for login response containing user information and authentication status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    
    private boolean success;
    private String message;
    private UserDto user;
    private String token; // For future JWT implementation
    
    public static LoginResponse success(UserDto user) {
        return LoginResponse.builder()
                .success(true)
                .message("Login successful")
                .user(user)
                .build();
    }
    
    public static LoginResponse failure(String message) {
        return LoginResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
