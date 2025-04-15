package com.gamejoy.config.validation.customannotation;

import com.gamejoy.config.validation.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Invalid username: It must be at least 3 characters long and cannot contain " +
            "any special characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
