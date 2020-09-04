package com.topov.forum.validation.post.constraint;

import com.topov.forum.validation.post.validator.UniqueTitleOnCreateConstraintValidator;
import com.topov.forum.validation.post.validator.UniqueTitleOnEditConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueTitleOnCreateConstraintValidator.class, UniqueTitleOnEditConstraintValidator.class})
public @interface UniqueTitle {
    String message() default "Title must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
