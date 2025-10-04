package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.FaqDto;
import com.example.Prompt2CodeDemo.entities.Faq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FaqMapper {
    
    FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);
    
    @Mapping(target = "createdByUserId", source = "createdByUserId")
    @Mapping(target = "answeredByUserId", source = "answeredByUserId")
    FaqDto toDto(Faq faq);
    
    @Mapping(target = "createdByUserId", source = "createdByUserId")
    @Mapping(target = "answeredByUserId", source = "answeredByUserId")
    Faq toEntity(FaqDto faqDto);
    
    List<FaqDto> toDtoList(List<Faq> faqs);
}
