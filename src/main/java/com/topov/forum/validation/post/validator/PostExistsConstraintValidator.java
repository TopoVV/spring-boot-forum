package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.constraint.PostExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostExistsConstraintValidator implements ConstraintValidator<PostExists, Long> {
    private PostRepository postRepository;

    @Override
    public boolean isValid(Long postId, ConstraintValidatorContext ctx) {
        if (!postRepository.existsById(postId)) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("The post doesn't exist")
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
