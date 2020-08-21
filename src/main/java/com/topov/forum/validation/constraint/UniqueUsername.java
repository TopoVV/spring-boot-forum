package com.topov.forum.validation.constraint;

import com.topov.forum.validation.UniqueUsernameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUsernameConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "The specified username is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
