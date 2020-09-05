package com.topov.forum.validation.registration.validator;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.registration.constraint.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator
    implements ConstraintValidator<UniqueEmail, RegistrationRequest> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext ctx) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("email")
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
