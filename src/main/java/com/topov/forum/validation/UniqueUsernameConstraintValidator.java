package com.topov.forum.validation;

import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.constraint.UniqueUsername;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
@Component
public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {
   private UserRepository userRepository;

   public boolean isValid(String username, ConstraintValidatorContext ctx) {
      log.debug("Validating unique username on registration");
      return !userRepository.existsByUsername(username);
   }

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
