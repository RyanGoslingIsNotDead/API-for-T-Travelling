package ru.itis.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.api.dto.UserDto;
import ru.itis.api.entity.User;
import ru.itis.api.entity.UserTravel;


import java.util.List;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserDto mapToUserDto(User user);

    default UserDto userTravelToUserDto(UserTravel userTravel) {
        if (userTravel == null || userTravel.getUser() == null) {
            return null;
        }
        return mapToUserDto(userTravel.getUser());
    }

    default List<UserDto> userTravelListToUserDtoList(List<UserTravel> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream()
                .map(this::userTravelToUserDto)
                .collect(Collectors.toList());
    }

}