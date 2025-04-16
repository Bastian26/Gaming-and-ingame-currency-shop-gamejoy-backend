package com.gamejoy.domain.usermanagement.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// SonarQube: Add a private constructor to hide the implicit public one. Alternative @UtilityClass
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class UserApiPaths {

    // AUTH API
    public static final String AUTH_API_BASE_URL = "/api/v1/auth";
}
