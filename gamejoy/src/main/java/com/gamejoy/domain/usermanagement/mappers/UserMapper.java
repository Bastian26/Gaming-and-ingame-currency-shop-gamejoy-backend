package com.gamejoy.domain.usermanagement.mappers;

import com.gamejoy.domain.authentication.dto.SignUpDto;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    // Ignore the password column for the mapping
    @Mapping(target = "password", ignore = true)
    User signUpDtoToUser(SignUpDto signUpDto);

    List<User> toUserList(List<UserDto> userDtos);

    List<UserDto> toUserDtoList(List<User> user);
}
