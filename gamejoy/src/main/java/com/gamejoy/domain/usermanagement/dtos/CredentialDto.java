package com.gamejoy.domain.usermanagement.dtos;

// From the security aspect it is better to use char instead of string for pw
public record CredentialDto(String userName, char[] password) {
}
