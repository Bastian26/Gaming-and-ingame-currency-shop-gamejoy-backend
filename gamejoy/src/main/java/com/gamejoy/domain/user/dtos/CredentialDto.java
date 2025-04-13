package com.gamejoy.domain.user.dtos;

import com.gamejoy.config.validation.customAnnotation.ValidPassword;
import com.gamejoy.config.validation.customAnnotation.ValidUsername;

// From the security aspect it is better to use char instead of string for pw
public record CredentialDto(String userName, char[] password) {
}
