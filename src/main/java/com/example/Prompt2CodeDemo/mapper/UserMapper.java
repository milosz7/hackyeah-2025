package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",uses = RoleMapper.class,  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    

    UserDto toDto(User user);
    
    User toEntity(UserDto userDto);
    
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}