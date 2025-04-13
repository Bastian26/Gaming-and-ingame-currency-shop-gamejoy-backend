package com.gamejoy.domain.user.dtos;

import com.gamejoy.config.validation.customAnnotation.ValidPassword;
import com.gamejoy.config.validation.customAnnotation.ValidUsername;
import com.gamejoy.domain.user.entities.Address;

public record SignUpDto(String firstName, String lastName, @ValidUsername String userName, @ValidPassword char[] password, String email, String telNr, Address address) {
}
