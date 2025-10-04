package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;
import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import com.example.Prompt2CodeDemo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = RoleMapper.class)
public interface UserMapper {
    
    UserDto toDto(User user);
    
    User toEntity(UserDto userDto);
    
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
    
    // Manual mapping methods to avoid circular dependency
    default SupervisedEntityDto supervisedEntityToSupervisedEntityDto(SupervisedEntity supervisedEntity) {
        if (supervisedEntity == null) {
            return null;
        }
        return SupervisedEntityDto.builder()
                .id(supervisedEntity.getId())
                .entityType(supervisedEntity.getEntityType())
                .uknfCode(supervisedEntity.getUknfCode())
                .entityName(supervisedEntity.getEntityName())
                .lei(supervisedEntity.getLei())
                .nip(supervisedEntity.getNip())
                .krs(supervisedEntity.getKrs())
                .street(supervisedEntity.getStreet())
                .buildingNumber(supervisedEntity.getBuildingNumber())
                .premisesNumber(supervisedEntity.getPremisesNumber())
                .postalCode(supervisedEntity.getPostalCode())
                .city(supervisedEntity.getCity())
                .phoneNumber(supervisedEntity.getPhoneNumber())
                .email(supervisedEntity.getEmail())
                .uknfRegistrationNumber(supervisedEntity.getUknfRegistrationNumber())
                .entityStatus(supervisedEntity.getEntityStatus())
                .entityCategory(supervisedEntity.getEntityCategory())
                .operatorSector(supervisedEntity.getOperatorSector())
                .entitySubsector(supervisedEntity.getEntitySubsector())
                .isCrossBorder(supervisedEntity.getIsCrossBorder())
                .createdAt(supervisedEntity.getCreatedAt())
                .updatedAt(supervisedEntity.getUpdatedAt())
                .build();
    }
    
    default SupervisedEntity supervisedEntityDtoToSupervisedEntity(SupervisedEntityDto supervisedEntityDto) {
        if (supervisedEntityDto == null) {
            return null;
        }
        SupervisedEntity supervisedEntity = new SupervisedEntity();
        supervisedEntity.setId(supervisedEntityDto.getId());
        supervisedEntity.setEntityType(supervisedEntityDto.getEntityType());
        supervisedEntity.setUknfCode(supervisedEntityDto.getUknfCode());
        supervisedEntity.setEntityName(supervisedEntityDto.getEntityName());
        supervisedEntity.setLei(supervisedEntityDto.getLei());
        supervisedEntity.setNip(supervisedEntityDto.getNip());
        supervisedEntity.setKrs(supervisedEntityDto.getKrs());
        supervisedEntity.setStreet(supervisedEntityDto.getStreet());
        supervisedEntity.setBuildingNumber(supervisedEntityDto.getBuildingNumber());
        supervisedEntity.setPremisesNumber(supervisedEntityDto.getPremisesNumber());
        supervisedEntity.setPostalCode(supervisedEntityDto.getPostalCode());
        supervisedEntity.setCity(supervisedEntityDto.getCity());
        supervisedEntity.setPhoneNumber(supervisedEntityDto.getPhoneNumber());
        supervisedEntity.setEmail(supervisedEntityDto.getEmail());
        supervisedEntity.setUknfRegistrationNumber(supervisedEntityDto.getUknfRegistrationNumber());
        supervisedEntity.setEntityStatus(supervisedEntityDto.getEntityStatus());
        supervisedEntity.setEntityCategory(supervisedEntityDto.getEntityCategory());
        supervisedEntity.setOperatorSector(supervisedEntityDto.getOperatorSector());
        supervisedEntity.setEntitySubsector(supervisedEntityDto.getEntitySubsector());
        supervisedEntity.setIsCrossBorder(supervisedEntityDto.getIsCrossBorder());
        supervisedEntity.setCreatedAt(supervisedEntityDto.getCreatedAt());
        supervisedEntity.setUpdatedAt(supervisedEntityDto.getUpdatedAt());
        return supervisedEntity;
    }
}