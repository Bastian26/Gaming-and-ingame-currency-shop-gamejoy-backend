package com.gamejoy.domain.usermanagement.dtos;

import com.gamejoy.config.validation.customannotation.ValidPassword;
import com.gamejoy.config.validation.customannotation.ValidUsername;
import com.gamejoy.domain.usermanagement.entities.Address;

public record SignUpDto(String firstName, String lastName, @ValidUsername String userName, @ValidPassword char[] password, String email, String telNr, Address address) {
}
