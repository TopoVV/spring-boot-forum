package com.topov.forum.validation;

import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.constraint.UniqueTitle;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
@Component
public class EditPostUniqueTitleValidator implements ConstraintValidator<UniqueTitle, PostEditRequest> {
    private PostRepository postRepository;
    @Override
    public boolean isValid(PostEditRequest postEditRequest, ConstraintValidatorContext ctx) {
        log.debug("Validating post edition request");
        final String newTitle = postEditRequest.getNewTitle();
        if(!postEditRequest.getOldTitle().equals(newTitle)) {
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
