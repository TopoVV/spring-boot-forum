package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.constraint.TitleUnique;
import com.topov.forum.validation.post.rule.PostEditValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnEditConstraintValidator implements ConstraintValidator<TitleUnique, PostEditValidationRule> {
    private PostRepository postRepository;

    @Override
    public boolean isValid(PostEditValidationRule validation, ConstraintValidatorContext ctx) {
        final String newTitle = validation.getNewTitle();
        if(!validation.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                ctx.disableDefaultConstraintViolation();
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
