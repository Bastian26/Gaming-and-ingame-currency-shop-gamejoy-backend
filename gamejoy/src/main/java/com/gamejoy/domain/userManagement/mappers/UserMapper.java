package com.gamejoy.domain.userManagement.mappers;

import com.gamejoy.domain.userManagement.dtos.SignUpDto;
import com.gamejoy.domain.userManagement.dtos.UserDto;
import com.gamejoy.domain.userManagement.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    // Ignore the password column for the mapping
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
