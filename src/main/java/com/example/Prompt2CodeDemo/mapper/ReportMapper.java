package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.ReportDto;
import com.example.Prompt2CodeDemo.entities.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportMapper {
    
    @Mapping(target = "supervisedEntityId", source = "supervisedEntity.id")
    @Mapping(target = "submittedByUserId", source = "submittedByUser.id")
    ReportDto toDto(Report report);
    
    @Mapping(target = "supervisedEntity", ignore = true)
    @Mapping(target = "submittedByUser", ignore = true)
    Report toEntity(ReportDto reportDto);
    
    void updateEntityFromDto(ReportDto reportDto, @MappingTarget Report report);
}

