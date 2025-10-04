package com.example.Prompt2CodeDemo.mapper;

import com.example.Prompt2CodeDemo.dto.SupervisedEntityDto;
import com.example.Prompt2CodeDemo.dto.UserDto;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import com.example.Prompt2CodeDemo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupervisedEntityMapper {
    
    SupervisedEntityDto toDto(SupervisedEntity supervisedEntity);
    
    SupervisedEntity toEntity(SupervisedEntityDto supervisedEntityDto);
    
    void updateEntityFromDto(SupervisedEntityDto supervisedEntityDto, @MappingTarget SupervisedEntity supervisedEntity);
    
    // Manual mapping methods to avoid circular dependency
    default UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userType(user.getUserType())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .build();
    }
    
    default User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserType(userDto.getUserType());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setIsActive(userDto.getIsActive());
        return user;
    }
}
