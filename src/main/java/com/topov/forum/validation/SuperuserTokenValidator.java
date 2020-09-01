package com.topov.forum.validation;

import com.topov.forum.service.token.SuperuserTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class SuperuserTokenValidator implements Validator<String> {
    private final SuperuserTokenService superuserTokenService;

    @Autowired
    public SuperuserTokenValidator(SuperuserTokenService superuserTokenService) {
        this.superuserTokenService = superuserTokenService;
    }

    @Override
    public ValidationResult validate(String token) {
        List<ValidationError> errors = new ArrayList<>();
        if (!superuserTokenService.isSuperuserTokenValid(token)) {
            final ValidationError validationError = new ValidationError("token", "Invalid token");
            errors.add(validationError);
        }
        return new ValidationResult(errors);
    }
}
