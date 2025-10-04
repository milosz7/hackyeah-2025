package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<UserDto> getAllUsers();
    
    Optional<UserDto> getUserById(Long id);
    
    Optional<UserDto> getUserByEmail(String email);
    
    UserDto createUser(UserDto userDto);
    
    Optional<UserDto> updateUser(Long id, UserDto userDto);
    
    boolean deleteUser(Long id);
    
    boolean existsByEmail(String email);
    
    // Role management methods
    Optional<UserDto> assignRoleToUser(Long userId, Integer roleId);
    
    Optional<UserDto> removeRoleFromUser(Long userId, Integer roleId);
    
    List<UserDto> getUsersByRole(Integer roleId);
}
