package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;

import java.util.List;
import java.util.Optional;

public interface SupervisedEntityService {
    
    List<SupervisedEntityDto> getAllSupervisedEntities();
    
    Optional<SupervisedEntityDto> getSupervisedEntityById(Long id);
    
    Optional<SupervisedEntityDto> getSupervisedEntityByUknfCode(String uknfCode);
    
    Optional<SupervisedEntityDto> getSupervisedEntityByNip(String nip);
    
    Optional<SupervisedEntityDto> getSupervisedEntityByKrs(String krs);
    
    Optional<SupervisedEntityDto> getSupervisedEntityByLei(String lei);
    
    SupervisedEntityDto createSupervisedEntity(SupervisedEntityDto supervisedEntityDto);
    
    Optional<SupervisedEntityDto> updateSupervisedEntity(Long id, SupervisedEntityDto supervisedEntityDto);
    
    boolean deleteSupervisedEntity(Long id);
    
    boolean existsByUknfCode(String uknfCode);
    
    boolean existsByNip(String nip);
    
    boolean existsByKrs(String krs);
    
    boolean existsByLei(String lei);
    
    // User-Entity relationship methods
    Optional<SupervisedEntityDto> assignUserToEntity(Long entityId, Long userId);
    
    Optional<SupervisedEntityDto> removeUserFromEntity(Long entityId, Long userId);
    
    List<SupervisedEntityDto> getEntitiesByUser(Long userId);
    
    List<SupervisedEntityDto> getEntitiesByStatus(String entityStatus);
    
    List<SupervisedEntityDto> getEntitiesByType(String entityType);
    
    List<SupervisedEntityDto> getCrossBorderEntities();
}
