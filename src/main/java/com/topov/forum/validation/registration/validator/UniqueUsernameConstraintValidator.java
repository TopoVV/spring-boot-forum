package com.topov.forum.validation.registration.validator;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.registration.constraint.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator
    implements ConstraintValidator<UniqueUsername, RegistrationRequest> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext ctx) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("username")
                .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
