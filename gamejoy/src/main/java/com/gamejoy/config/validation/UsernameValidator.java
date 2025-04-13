package com.gamejoy.config.validation;

import com.gamejoy.config.validation.customAnnotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validates the username. This must be at least 3 bays long and may not contain any special characters
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final int MIN_LENGTH = 3;
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
    }

    // Username must be at least 3 characters long and must not contain any special characters
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.length() < MIN_LENGTH) {
            return false;
        }

        // Checks that there are no special characters in the username
        return Pattern.matches("^[a-zA-Z0-9]+$", username);
    }
}
