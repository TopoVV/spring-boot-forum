package com.topov.forum.validation.registration.constraint;

import com.topov.forum.validation.registration.group.RegistrationChecks;
import com.topov.forum.validation.registration.validator.UniqueUsernameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUsernameConstraintValidator.class})
public @interface UniqueUsername {
    String message() default "The specified username is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
