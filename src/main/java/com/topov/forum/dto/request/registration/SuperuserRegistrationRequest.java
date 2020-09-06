package com.topov.forum.dto.request.registration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuperuserRegistrationRequest extends RegistrationRequest {
    @NotNull(message = "The token must not be empty")
    private String token;
}
