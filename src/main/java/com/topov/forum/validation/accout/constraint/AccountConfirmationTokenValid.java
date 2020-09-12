package com.topov.forum.validation.accout.constraint;

import com.topov.forum.validation.accout.validator.AccountConfirmationTokenConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AccountConfirmationTokenConstraintValidator.class})
public @interface AccountConfirmationTokenValid {
    String message() default "The provided confirmation token is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
