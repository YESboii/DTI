package com.example.todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unique {
    String message() default "Email already exists";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
