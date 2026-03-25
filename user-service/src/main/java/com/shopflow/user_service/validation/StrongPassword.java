package com.shopflow.user_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.*;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    // These 3 are mandatory for any constraint annotation
    String message() default "Password must be 8+ chars with uppercase, digit, and special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
