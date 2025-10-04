package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;
import com.example.Prompt2CodeDemo.service.SupervisedEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supervised-entities")
public class SupervisedEntityController {
    
    @Autowired
    private SupervisedEntityService supervisedEntityService;
    
    @GetMapping
    public ResponseEntity<List<SupervisedEntityDto>> getAllSupervisedEntities() {
        List<SupervisedEntityDto> entities = supervisedEntityService.getAllSupervisedEntities();
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SupervisedEntityDto> getSupervisedEntityById(@PathVariable Long id) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.getSupervisedEntityById(id);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/uknf-code/{uknfCode}")
    public ResponseEntity<SupervisedEntityDto> getSupervisedEntityByUknfCode(@PathVariable String uknfCode) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.getSupervisedEntityByUknfCode(uknfCode);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nip/{nip}")
    public ResponseEntity<SupervisedEntityDto> getSupervisedEntityByNip(@PathVariable String nip) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.getSupervisedEntityByNip(nip);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/krs/{krs}")
    public ResponseEntity<SupervisedEntityDto> getSupervisedEntityByKrs(@PathVariable String krs) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.getSupervisedEntityByKrs(krs);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/lei/{lei}")
    public ResponseEntity<SupervisedEntityDto> getSupervisedEntityByLei(@PathVariable String lei) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.getSupervisedEntityByLei(lei);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<SupervisedEntityDto> createSupervisedEntity(@Valid @RequestBody SupervisedEntityDto supervisedEntityDto) {
        SupervisedEntityDto createdEntity = supervisedEntityService.createSupervisedEntity(supervisedEntityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SupervisedEntityDto> updateSupervisedEntity(@PathVariable Long id, @Valid @RequestBody SupervisedEntityDto supervisedEntityDto) {
        Optional<SupervisedEntityDto> updatedEntity = supervisedEntityService.updateSupervisedEntity(id, supervisedEntityDto);
        return updatedEntity.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupervisedEntity(@PathVariable Long id) {
        boolean deleted = supervisedEntityService.deleteSupervisedEntity(id);
        return deleted ? ResponseEntity.ok().build() 
                       : ResponseEntity.notFound().build();
    }
    
    // User-Entity relationship endpoints
    @PostMapping("/{entityId}/users/{userId}")
    public ResponseEntity<SupervisedEntityDto> assignUserToEntity(@PathVariable Long entityId, @PathVariable Long userId) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.assignUserToEntity(entityId, userId);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{entityId}/users/{userId}")
    public ResponseEntity<SupervisedEntityDto> removeUserFromEntity(@PathVariable Long entityId, @PathVariable Long userId) {
        Optional<SupervisedEntityDto> entity = supervisedEntityService.removeUserFromEntity(entityId, userId);
        return entity.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<SupervisedEntityDto>> getEntitiesByUser(@PathVariable Long userId) {
        List<SupervisedEntityDto> entities = supervisedEntityService.getEntitiesByUser(userId);
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<SupervisedEntityDto>> getEntitiesByStatus(@PathVariable String status) {
        List<SupervisedEntityDto> entities = supervisedEntityService.getEntitiesByStatus(status);
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<SupervisedEntityDto>> getEntitiesByType(@PathVariable String type) {
        List<SupervisedEntityDto> entities = supervisedEntityService.getEntitiesByType(type);
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/cross-border")
    public ResponseEntity<List<SupervisedEntityDto>> getCrossBorderEntities() {
        List<SupervisedEntityDto> entities = supervisedEntityService.getCrossBorderEntities();
        return ResponseEntity.ok(entities);
    }
}
