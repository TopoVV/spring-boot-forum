package com.topov.forum.validation.constraint;

import com.topov.forum.validation.UniqueEmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "The specified email is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
