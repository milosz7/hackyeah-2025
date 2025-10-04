package com.example.Prompt2CodeDemo.dao.interfaces;

import com.example.Prompt2CodeDemo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
    
    Optional<User> findByEmail(String email);
    
    User save(User user);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    boolean existsByEmail(String email);
    
    long count();
}
