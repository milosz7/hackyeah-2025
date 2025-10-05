package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.UserDao;
import com.example.Prompt2CodeDemo.dto.LoginRequest;
import com.example.Prompt2CodeDemo.dto.LoginResponse;
import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.User;
import com.example.Prompt2CodeDemo.mapper.UserMapper;
import com.example.Prompt2CodeDemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation for authentication-related operations
 * Handles user login, logout, and other authentication logic
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Find user by email
            Optional<User> userOptional = userDao.findByEmail(loginRequest.getEmail());
            
            if (userOptional.isEmpty()) {
                return LoginResponse.failure("Invalid email or password");
            }
            
            User user = userOptional.get();
            
            // Check if user is active
            if (!user.getIsActive()) {
                return LoginResponse.failure("Account is deactivated");
            }
            
            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return LoginResponse.failure("Invalid email or password");
            }
            
            // Convert user to DTO and return success response
            UserDto userDto = userMapper.toDto(user);
            return LoginResponse.success(userDto);
            
        } catch (Exception e) {
            // Log the exception for debugging
            return LoginResponse.failure("Login failed due to an internal error");
        }
    }
    
}
