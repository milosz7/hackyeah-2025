package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.SupervisedEntityDao;
import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import com.example.Prompt2CodeDemo.entities.User;
import com.example.Prompt2CodeDemo.mapper.SupervisedEntityMapper;
import com.example.Prompt2CodeDemo.service.SupervisedEntityService;
import com.example.Prompt2CodeDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupervisedEntityServiceImpl implements SupervisedEntityService {
    
    @Autowired
    private SupervisedEntityDao supervisedEntityDao;
    
    @Autowired
    private SupervisedEntityMapper supervisedEntityMapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntityDto> getAllSupervisedEntities() {
        return supervisedEntityDao.findAll()
                .stream()
                .map(supervisedEntityMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntityDto> getSupervisedEntityById(Long id) {
        return supervisedEntityDao.findById(id)
                .map(supervisedEntityMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntityDto> getSupervisedEntityByUknfCode(String uknfCode) {
        return supervisedEntityDao.findByUknfCode(uknfCode)
                .map(supervisedEntityMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntityDto> getSupervisedEntityByNip(String nip) {
        return supervisedEntityDao.findByNip(nip)
                .map(supervisedEntityMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntityDto> getSupervisedEntityByKrs(String krs) {
        return supervisedEntityDao.findByKrs(krs)
                .map(supervisedEntityMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntityDto> getSupervisedEntityByLei(String lei) {
        return supervisedEntityDao.findByLei(lei)
                .map(supervisedEntityMapper::toDto);
    }
    
    @Override
    public SupervisedEntityDto createSupervisedEntity(SupervisedEntityDto supervisedEntityDto) {
        SupervisedEntity supervisedEntity = supervisedEntityMapper.toEntity(supervisedEntityDto);
        SupervisedEntity savedEntity = supervisedEntityDao.save(supervisedEntity);
        return supervisedEntityMapper.toDto(savedEntity);
    }
    
    @Override
    public Optional<SupervisedEntityDto> updateSupervisedEntity(Long id, SupervisedEntityDto supervisedEntityDto) {
        return supervisedEntityDao.findById(id)
                .map(existingEntity -> {
                    supervisedEntityMapper.updateEntityFromDto(supervisedEntityDto, existingEntity);
                    SupervisedEntity updatedEntity = supervisedEntityDao.save(existingEntity);
                    return supervisedEntityMapper.toDto(updatedEntity);
                });
    }
    
    @Override
    public boolean deleteSupervisedEntity(Long id) {
        if (supervisedEntityDao.existsById(id)) {
            supervisedEntityDao.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUknfCode(String uknfCode) {
        return supervisedEntityDao.existsByUknfCode(uknfCode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByNip(String nip) {
        return supervisedEntityDao.existsByNip(nip);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByKrs(String krs) {
        return supervisedEntityDao.existsByKrs(krs);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByLei(String lei) {
        return supervisedEntityDao.existsByLei(lei);
    }
    
    @Override
    public Optional<SupervisedEntityDto> assignUserToEntity(Long entityId, Long userId) {
        return supervisedEntityDao.findById(entityId)
                .flatMap(entity -> userService.getUserById(userId)
                        .map(userDto -> {
                            User user = new User();
                            user.setId(userDto.getId());
                            user.setFirstName(userDto.getFirstName());
                            user.setLastName(userDto.getLastName());
                            user.setEmail(userDto.getEmail());
                            user.setUserType(userDto.getUserType());
                            user.setPhoneNumber(userDto.getPhoneNumber());
                            
                            if (entity.getUsers() == null) {
                                entity.setUsers(new java.util.HashSet<>());
                            }
                            entity.getUsers().add(user);
                            
                            SupervisedEntity savedEntity = supervisedEntityDao.save(entity);
                            return supervisedEntityMapper.toDto(savedEntity);
                        }));
    }
    
    @Override
    public Optional<SupervisedEntityDto> removeUserFromEntity(Long entityId, Long userId) {
        return supervisedEntityDao.findById(entityId)
                .map(entity -> {
                    if (entity.getUsers() != null) {
                        entity.getUsers().removeIf(user -> user.getId().equals(userId));
                        SupervisedEntity savedEntity = supervisedEntityDao.save(entity);
                        return supervisedEntityMapper.toDto(savedEntity);
                    }
                    return supervisedEntityMapper.toDto(entity);
                });
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntityDto> getEntitiesByUser(Long userId) {
        return supervisedEntityDao.findByUsersId(userId)
                .stream()
                .map(supervisedEntityMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntityDto> getEntitiesByStatus(String entityStatus) {
        return supervisedEntityDao.findByEntityStatus(entityStatus)
                .stream()
                .map(supervisedEntityMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntityDto> getEntitiesByType(String entityType) {
        return supervisedEntityDao.findByEntityType(entityType)
                .stream()
                .map(supervisedEntityMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntityDto> getCrossBorderEntities() {
        return supervisedEntityDao.findByIsCrossBorder(true)
                .stream()
                .map(supervisedEntityMapper::toDto)
                .collect(Collectors.toList());
    }
}
