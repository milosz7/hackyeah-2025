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
public class RoleDto {
    
    private Integer id;
    private String name;
    private String permissions;
    private Set<UserDto> users;
}
