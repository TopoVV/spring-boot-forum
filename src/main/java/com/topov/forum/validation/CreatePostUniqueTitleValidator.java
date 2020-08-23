package com.topov.forum.validation;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.constraint.UniqueTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CreatePostUniqueTitleValidator implements ConstraintValidator<UniqueTitle, CreatePostRequest> {
   private PostRepository postRepository;

   public boolean isValid(CreatePostRequest createPostRequest, ConstraintValidatorContext ctx) {
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
