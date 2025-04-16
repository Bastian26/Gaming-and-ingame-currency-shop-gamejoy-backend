package com.gamejoy.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Configures the PasswordEncoder for the application.
 * Uses BCrypt to securely hash passwords instead of storing them in plain text.
 * This bean is typically used in services like user registration and authentication.
 */
@Component
public class PasswordConfig {

    // Necessary to not store passwords in plain text - BCrypt is a good option
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
