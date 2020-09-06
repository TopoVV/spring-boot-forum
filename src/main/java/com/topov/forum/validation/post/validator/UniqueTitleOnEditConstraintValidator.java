package com.topov.forum.validation.post.validator;

import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.PostEditValidation;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnEditConstraintValidator implements ConstraintValidator<UniqueTitle, PostEditValidation> {
    private PostRepository postRepository;

    @Override
    public boolean isValid(PostEditValidation validation, ConstraintValidatorContext ctx) {
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
