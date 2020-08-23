package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegistrationResponse extends OperationResponse {
    public RegistrationResponse(String message) {
        super(message);
    }
}
