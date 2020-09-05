package com.topov.forum.validation.registration.constraint;

import com.topov.forum.validation.registration.validator.SuperuserTokenConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SuperuserTokenConstraintValidator.class })
public @interface ValidSuperuserToken {
    String message() default "Provided superuser token is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
