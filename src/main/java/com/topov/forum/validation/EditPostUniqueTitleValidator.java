package com.topov.forum.validation;

import com.topov.forum.dto.request.EditPostRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.constraint.UniqueTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EditPostUniqueTitleValidator implements ConstraintValidator<UniqueTitle, EditPostRequest> {
    private PostRepository postRepository;
    @Override
    public boolean isValid(EditPostRequest editPostRequest, ConstraintValidatorContext ctx) {
        final String newTitle = editPostRequest.getNewTitle();
        if(!editPostRequest.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("newTitle")
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
