package com.example.Prompt2CodeDemo.dao.interfaces;

import com.example.Prompt2CodeDemo.entities.SupervisedEntity;

import java.util.List;
import java.util.Optional;

public interface SupervisedEntityDao {
    
    List<SupervisedEntity> findAll();
    
    Optional<SupervisedEntity> findById(Long id);
    
    Optional<SupervisedEntity> findByUknfCode(String uknfCode);
    
    Optional<SupervisedEntity> findByNip(String nip);
    
    Optional<SupervisedEntity> findByKrs(String krs);
    
    Optional<SupervisedEntity> findByLei(String lei);
    
    SupervisedEntity save(SupervisedEntity supervisedEntity);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    boolean existsByUknfCode(String uknfCode);
    
    boolean existsByNip(String nip);
    
    boolean existsByKrs(String krs);
    
    boolean existsByLei(String lei);
    
    long count();
    
    // Query methods
    List<SupervisedEntity> findByEntityStatus(String entityStatus);
    
    List<SupervisedEntity> findByEntityType(String entityType);
    
    List<SupervisedEntity> findByIsCrossBorder(Boolean isCrossBorder);
    
    List<SupervisedEntity> findByUsersId(Long userId);
    
    List<SupervisedEntity> findByCity(String city);
    
    List<SupervisedEntity> findByEntityCategory(String entityCategory);
}
