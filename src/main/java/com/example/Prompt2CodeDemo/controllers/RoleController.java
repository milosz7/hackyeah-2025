package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.RoleDto;
import com.example.Prompt2CodeDemo.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Integer id) {
        Optional<RoleDto> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<RoleDto> getRoleByName(@PathVariable String name) {
        Optional<RoleDto> role = roleService.getRoleByName(name);
        return role.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleDto roleDto) {
        Optional<RoleDto> updatedRole = roleService.updateRole(id, roleDto);
        return updatedRole.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        boolean deleted = roleService.deleteRole(id);
        return deleted ? ResponseEntity.ok().build() 
                       : ResponseEntity.notFound().build();
    }
}
