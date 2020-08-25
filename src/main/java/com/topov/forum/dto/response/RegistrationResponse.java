package com.topov.forum.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegistrationResponse extends OperationResponse {
    public RegistrationResponse(String message) {
        super(message);
    }
}
