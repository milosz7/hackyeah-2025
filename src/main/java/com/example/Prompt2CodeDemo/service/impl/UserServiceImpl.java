package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.UserDao;
import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.User;
import com.example.Prompt2CodeDemo.mapper.UserMapper;
import com.example.Prompt2CodeDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userDao.findById(id)
                .map(userMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userDao.findByEmail(email)
                .map(userMapper::toDto);
    }
    
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userDao.save(user);
        return userMapper.toDto(savedUser);
    }
    
    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return userDao.findById(id)
                .map(existingUser -> {
                    userMapper.updateEntityFromDto(userDto, existingUser);
                    User updatedUser = userDao.save(existingUser);
                    return userMapper.toDto(updatedUser);
                });
    }
    
    @Override
    public boolean deleteUser(Long id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }
}
