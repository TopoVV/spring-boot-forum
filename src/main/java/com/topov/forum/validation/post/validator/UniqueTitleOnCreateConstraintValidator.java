package com.topov.forum.validation.post.validator;

import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueTitleOnCreateConstraintValidator implements ConstraintValidator<UniqueTitle, String> {
   private PostRepository postRepository;

   public boolean isValid(String title, ConstraintValidatorContext ctx) {
      return !postRepository.existsByTitle(title);
   }

   @Autowired
   public void setPostRepository(PostRepository postRepository) {
      this.postRepository = postRepository;
   }
}
