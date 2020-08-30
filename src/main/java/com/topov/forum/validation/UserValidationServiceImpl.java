package com.topov.forum.validation;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.service.token.SuperuserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserValidationServiceImpl implements UserValidationService {
    private final UserRepository userRepository;
    private final SuperuserTokenService superuserTokenService;

    @Autowired
    public UserValidationServiceImpl(UserRepository repository, SuperuserTokenService superuserTokenService) {
        userRepository = repository;
        this.superuserTokenService = superuserTokenService;
    }

    @Override
    public ValidationResult validateRegistration(RegistrationRequest registrationRequest) {
        List<ValidationError> errors = validateUsernameAndEmail(registrationRequest);
        return new ValidationResult(errors);
    }

    @Override
    public ValidationResult validateSuperuserRegistration(SuperuserRegistrationRequest registrationRequest) {
        List<ValidationError> errors = new ArrayList<>();
        if (!superuserTokenService.isSuperuserTokenValid(registrationRequest.getToken())) {
            final ValidationError validationError = new ValidationError("token", "Invalid token");
            errors.add(validationError);
            return new ValidationResult(errors);
        }

        List<ValidationError> usernameAndEmailErrors = validateUsernameAndEmail(registrationRequest);
        errors.addAll(usernameAndEmailErrors);
        return new ValidationResult(errors);
    }

    private List<ValidationError> validateUsernameAndEmail(RegistrationRequest registrationRequest) {
        List<ValidationError> errors = new ArrayList<>();
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            final ValidationError error = new ValidationError("email", "Specified email is already in use");
            errors.add(error);
        }

        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            final ValidationError error = new ValidationError("username", "Specified username is already in use");
            errors.add(error);
        }
        return errors;
    }
}
