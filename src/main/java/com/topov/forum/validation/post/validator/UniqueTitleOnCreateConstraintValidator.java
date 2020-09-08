package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.constraint.TitleUnique;
import com.topov.forum.validation.post.validation.PostCreateValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnCreateConstraintValidator implements ConstraintValidator<TitleUnique, PostCreateValidationRule> {
   private PostRepository postRepository;

   public boolean isValid(PostCreateValidationRule validation, ConstraintValidatorContext ctx) {
      if(postRepository.existsByTitle(validation.getTitle())) {
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
