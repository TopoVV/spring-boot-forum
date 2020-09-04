package com.topov.forum.validation.registration;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Log4j2
@Service
public class RegistrationDataValidator implements Validator<RegistrationRequest> {
    private final UserRepository userRepository;

    public RegistrationDataValidator(UserRepository repository) {
        userRepository = repository;
    }

    @Override
    public ValidationResult validate(RegistrationRequest data) {
        List<Error> errors = new ArrayList<>();
        if (userRepository.existsByEmail(data.getEmail())) {
            final Error error = new ValidationError("email", "Specified email is already in use");
            errors.add(error);
        }

        if (userRepository.existsByUsername(data.getUsername())) {
            final Error error = new ValidationError("username", "Specified username is already in use");
            errors.add(error);
        }

        return new ValidationResult(List.of());
    }
}
