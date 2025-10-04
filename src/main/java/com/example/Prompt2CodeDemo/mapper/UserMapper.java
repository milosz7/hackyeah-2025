package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import java.util.stream.Collectors;
import com.example.Prompt2CodeDemo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",uses = RoleMapper.class,  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    

    //@Mapping(target = "roles", ignore = true)
    UserDto toDto(User user);
    
    // @AfterMapping
    // private void afterMapping(User user,@MappingTarget UserDto userDto) {
    //     userDto.setRoles(user.getRoles().stream().map(roleMapper::toDto).collect(Collectors.toSet()));
    // }

    User toEntity(UserDto userDto);
    
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}