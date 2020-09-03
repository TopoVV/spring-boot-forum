package com.topov.forum.dto.request.registration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuperuserRegistrationRequest extends RegistrationRequest {
    @NotEmpty(message = "The token must not be empty")
    private String token;

    public SuperuserRegistrationRequest(String token, String username, String password, String email) {
        super(username, password, email);
        this.token =  token;
    }
}
