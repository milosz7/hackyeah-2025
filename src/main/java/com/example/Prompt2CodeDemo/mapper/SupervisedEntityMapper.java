package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupervisedEntityMapper {
    
    SupervisedEntityDto toDto(SupervisedEntity supervisedEntity);
    
    SupervisedEntity toEntity(SupervisedEntityDto supervisedEntityDto);
    
    void updateEntityFromDto(SupervisedEntityDto supervisedEntityDto, @MappingTarget SupervisedEntity supervisedEntity);
}
