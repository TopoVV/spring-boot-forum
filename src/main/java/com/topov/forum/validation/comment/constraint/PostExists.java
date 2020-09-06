package com.topov.forum.validation.comment.constraint;

import com.topov.forum.validation.post.validator.PostExistsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PostExistsConstraintValidator.class })
public @interface PostExists {
    String message() default "The post doesnt exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
