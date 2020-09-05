package com.topov.forum.validation.comment.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.comment.CommentValidator;
import com.topov.forum.validation.comment.constraint.PostExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.topov.forum.validation.comment.CommentValidator.*;

public class PostExistsConstraintValidator
    implements ConstraintValidator<PostExists, PostExistsRule> {

    private PostRepository postRepository;

    @Override
    public boolean isValid(PostExistsRule postExistsRule, ConstraintValidatorContext ctx) {
        if (postRepository.existsById(postExistsRule.getPostId())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("post")
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
