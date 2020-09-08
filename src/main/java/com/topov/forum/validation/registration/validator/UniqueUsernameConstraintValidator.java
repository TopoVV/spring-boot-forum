package com.topov.forum.validation.registration.validator;

import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.registration.constraint.UsernameUnique;
import com.topov.forum.validation.registration.validation.RegistrationValidationRule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator
    implements ConstraintValidator<UsernameUnique, RegistrationValidationRule> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(RegistrationValidationRule validation, ConstraintValidatorContext ctx) {
        if (userRepository.existsByUsername(validation.getUsername())) {
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
