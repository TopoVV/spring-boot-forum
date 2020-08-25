package com.topov.forum.validation;

import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.constraint.UniqueEmail;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
@Component
public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {
   private UserRepository userRepository;

   public boolean isValid(String email, ConstraintValidatorContext ctx) {
      log.debug("Validating unique email on registration");
      return !userRepository.existsByEmail(email);
   }

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
