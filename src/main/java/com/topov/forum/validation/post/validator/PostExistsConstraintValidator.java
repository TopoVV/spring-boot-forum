package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.comment.constraint.PostExists;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.lang.model.type.ErrorType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostExistsConstraintValidator implements ConstraintValidator<PostExists, Long> {
    private PostRepository postRepository;

    @Override
    public boolean isValid(Long postId, ConstraintValidatorContext ctx) {
        if (!postRepository.existsById(postId)) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("title")
                .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
