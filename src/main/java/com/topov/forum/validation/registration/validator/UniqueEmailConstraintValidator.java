package com.topov.forum.validation.registration.validator;

import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.registration.constraint.EmailUnique;
import com.topov.forum.validation.registration.rules.RegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator
    implements ConstraintValidator<EmailUnique, RegistrationValidation> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(RegistrationValidation validation, ConstraintValidatorContext ctx) {
        if (userRepository.existsByEmail(validation.getEmail())) {
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
