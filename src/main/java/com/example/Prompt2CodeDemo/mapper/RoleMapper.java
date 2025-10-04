package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.RoleDto;
import com.example.Prompt2CodeDemo.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    
    RoleDto toDto(Role role);
    
    Role toEntity(RoleDto roleDto);
    
    void updateEntityFromDto(RoleDto roleDto, @MappingTarget Role role);
}
