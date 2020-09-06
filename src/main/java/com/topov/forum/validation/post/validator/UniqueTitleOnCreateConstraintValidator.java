package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.PostCreateValidation;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnCreateConstraintValidator implements ConstraintValidator<UniqueTitle, PostCreateValidation> {
   private PostRepository postRepository;

   public boolean isValid(PostCreateValidation validation, ConstraintValidatorContext ctx) {
      return !postRepository.existsByTitle(validation.getTitle());
   }

   @Autowired
   public void setPostRepository(PostRepository postRepository) {
      this.postRepository = postRepository;
   }
}
