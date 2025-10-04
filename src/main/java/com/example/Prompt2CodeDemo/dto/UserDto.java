package com.example.Prompt2CodeDemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    
    private String firstName;
    private String lastName;
    private String email;
    private String userType;
    private String phoneNumber;
    private Set<RoleDto> roles;
}