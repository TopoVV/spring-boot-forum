package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.constraint.TitleUnique;
import com.topov.forum.validation.post.rules.PostEditValidation;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnEditConstraintValidator implements ConstraintValidator<TitleUnique, PostEditValidation> {
    private PostRepository postRepository;

    @Override
    public boolean isValid(PostEditValidation validation, ConstraintValidatorContext context) {
        HibernateConstraintValidatorContext ctx = context.unwrap(HibernateConstraintValidatorContext.class);
        final String newTitle = validation.getNewTitle();
        if(!validation.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                ctx.disableDefaultConstraintViolation();
                ctx.withDynamicPayload(ErrorType.VALIDATION_ERROR);
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("title")
                    .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
