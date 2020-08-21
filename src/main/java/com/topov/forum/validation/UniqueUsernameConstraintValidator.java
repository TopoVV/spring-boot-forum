package com.topov.forum.validation;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.constraint.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {
   private UserRepository userRepository;

   public boolean isValid(String username, ConstraintValidatorContext ctx) {
      return !userRepository.existsByUsername(username);
   }

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
