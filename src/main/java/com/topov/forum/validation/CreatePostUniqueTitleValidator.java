package com.topov.forum.validation;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.constraint.UniqueTitle;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
@Component
public class CreatePostUniqueTitleValidator implements ConstraintValidator<UniqueTitle, CreatePostRequest> {
   private PostRepository postRepository;

   public boolean isValid(CreatePostRequest createPostRequest, ConstraintValidatorContext ctx) {
      log.debug("Validation post creation request");
      if(postRepository.existsByTitle(createPostRequest.getTitle())) {
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
