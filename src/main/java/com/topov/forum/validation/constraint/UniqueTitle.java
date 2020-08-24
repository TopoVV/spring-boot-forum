package com.topov.forum.validation.constraint;

import com.topov.forum.validation.CreatePostUniqueTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CreatePostUniqueTitleValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTitle {
    String message() default "A post with such title already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
