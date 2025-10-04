package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.RoleDao;
import com.example.Prompt2CodeDemo.dto.RoleDto;
import com.example.Prompt2CodeDemo.entities.Role;
import com.example.Prompt2CodeDemo.mapper.RoleMapper;
import com.example.Prompt2CodeDemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleDao.findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleById(Integer id) {
        return roleDao.findById(id)
                .map(roleMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleByName(String name) {
        return roleDao.findByName(name)
                .map(roleMapper::toDto);
    }
    
    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleDao.save(role);
        return roleMapper.toDto(savedRole);
    }
    
    @Override
    public Optional<RoleDto> updateRole(Integer id, RoleDto roleDto) {
        return roleDao.findById(id)
                .map(existingRole -> {
                    roleMapper.updateEntityFromDto(roleDto, existingRole);
                    Role updatedRole = roleDao.save(existingRole);
                    return roleMapper.toDto(updatedRole);
                });
    }
    
    @Override
    public boolean deleteRole(Integer id) {
        if (roleDao.existsById(id)) {
            roleDao.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleDao.existsByName(name);
    }
}
