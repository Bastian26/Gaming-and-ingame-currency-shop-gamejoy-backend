package com.gamejoy.config.validation.customannotation;

import com.gamejoy.config.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password: It must be at least 8 characters long and contain " +
            "uppercase and lowercase letters, as well as special characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
