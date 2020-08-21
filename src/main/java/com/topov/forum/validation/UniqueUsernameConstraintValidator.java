package com.topov.forum.validation;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.repository.ForumUserRepository;
import com.topov.forum.service.UserService;
import com.topov.forum.validation.constraint.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, RegistrationRequest> {
   private ForumUserRepository userRepository;

   public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext ctx) {
      if(userRepository.existsByUsername(registrationRequest.getUsername())) {
         ctx.disableDefaultConstraintViolation();
         ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
            .addPropertyNode("username")
            .addConstraintViolation();
         return false;
      }
      return true;
   }

   @Autowired
   public void setUserRepository(ForumUserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
