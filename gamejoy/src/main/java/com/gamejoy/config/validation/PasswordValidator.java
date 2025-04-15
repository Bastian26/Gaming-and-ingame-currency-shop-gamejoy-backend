package com.gamejoy.config.validation;

import com.gamejoy.config.validation.customannotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validates the password. This must be at least 8. In addition, there must be at least 1 uppercase,
 * 1 lowercase letter, 1 special character as well as a number
 */
@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, char[]> {

    private static final int MIN_LENGTH = 8;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    // Password must be at least 3 characters long and must not contain any special characters
    @Override
    public boolean isValid(char[] charPassword, ConstraintValidatorContext context) {


        if (charPassword == null || charPassword.length < MIN_LENGTH) {
            return false;
        }
        String password = String.valueOf(charPassword);

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Pattern.matches("[^a-zA-Z0-9]", String.valueOf(c))) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}
