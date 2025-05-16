package ru.itis.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.api.dto.UserDto;
import ru.itis.api.entity.User;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserMapper {
    
    UserDto mapToProfileDto(User user);
}
