package com.topov.forum.dto.request.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotEmpty(message = "The username must not be empty")
    protected String username;
    @NotEmpty(message = "The password must not be empty")
    protected String password;
    @Email(message = "The email is incorrect")
    @NotEmpty(message = "The email must not be empty")
    protected String email;
}
