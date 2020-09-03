package com.topov.forum.validation.registration;

<<<<<<< HEAD
import com.topov.forum.dto.error.Error;
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.dto.error.ValidationError;
=======
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.validation.ValidationError;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.Validator;
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
<<<<<<< HEAD
        List<Error> errors = new ArrayList<>();
        if (!superuserTokenService.isSuperuserTokenValid(token)) {
            final Error validationError = new ValidationError("token", "Invalid token");
=======
        List<ValidationError> errors = new ArrayList<>();
        if (!superuserTokenService.isSuperuserTokenValid(token)) {
            final ValidationError validationError = new ValidationError("token", "Invalid token");
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
            errors.add(validationError);
        }
        return new ValidationResult(errors);
    }
}
