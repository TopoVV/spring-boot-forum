package com.topov.forum.validation;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.repository.ForumUserRepository;
import com.topov.forum.validation.constraint.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, RegistrationRequest> {
   private ForumUserRepository userRepository;

   public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext ctx) {
      if(userRepository.existsByEmail(registrationRequest.getEmail())) {
         ctx.disableDefaultConstraintViolation();
         ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
            .addPropertyNode("email")
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
