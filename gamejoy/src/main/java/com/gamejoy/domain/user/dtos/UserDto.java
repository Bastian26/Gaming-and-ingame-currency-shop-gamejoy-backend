package com.gamejoy.domain.user.dtos;

import com.gamejoy.domain.user.entities.Address;
import com.gamejoy.domain.user.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String telNr;
    private Address address;
    private UserRole userRole;
    private String token;
}
