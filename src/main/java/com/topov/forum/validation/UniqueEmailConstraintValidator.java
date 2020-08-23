package com.topov.forum.validation;

import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.constraint.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {
   private UserRepository userRepository;

   public boolean isValid(String email, ConstraintValidatorContext ctx) {
      return !userRepository.existsByEmail(email);
   }

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
