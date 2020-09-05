package com.topov.forum.validation.registration.constraint;

import com.topov.forum.validation.registration.validator.UniqueEmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueEmailConstraintValidator.class})
public @interface UniqueEmail {
    String message() default "The specified email is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
