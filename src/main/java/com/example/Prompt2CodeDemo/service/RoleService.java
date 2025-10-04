package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    
    List<RoleDto> getAllRoles();
    
    Optional<RoleDto> getRoleById(Integer id);
    
    Optional<RoleDto> getRoleByName(String name);
    
    RoleDto createRole(RoleDto roleDto);
    
    Optional<RoleDto> updateRole(Integer id, RoleDto roleDto);
    
    boolean deleteRole(Integer id);
    
    boolean existsByName(String name);
}
