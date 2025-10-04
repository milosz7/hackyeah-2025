package com.example.Prompt2CodeDemo.dao.interfaces;

import com.example.Prompt2CodeDemo.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    
    List<Role> findAll();
    
    Optional<Role> findById(Integer id);
    
    Optional<Role> findByName(String name);
    
    Role save(Role role);
    
    void deleteById(Integer id);
    
    boolean existsById(Integer id);
    
    boolean existsByName(String name);
    
    long count();
}
