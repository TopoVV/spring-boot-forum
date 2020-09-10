package com.topov.forum.validation.comment.constraint;

import com.topov.forum.validation.comment.validator.CommentExistsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CommentExistsConstraintValidator.class})
public @interface CommentExists {
    String message() default "Comment does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
