package com.gamejoy.domain.authentication.dto;

// From the security aspect it is better to use char instead of string for pw
public record CredentialDto(String userName, char[] password) {
}
