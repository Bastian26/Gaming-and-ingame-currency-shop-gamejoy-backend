package com.gamejoy.domain.user.controllers.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// SonarQube: Add a private constructor to hide the implicit public one.
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class UserApiPaths {

    // AUTH API
    public static final String AUTH_API_BASE_URL = "/api/v1/auth";
    public static final String AUTH_API_LOGIN = AUTH_API_BASE_URL + "/login";
    public static final String AUTH_API_REGISTER = AUTH_API_BASE_URL + "/register";
}
