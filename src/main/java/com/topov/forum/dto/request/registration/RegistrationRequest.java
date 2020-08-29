package com.topov.forum.dto.request.registration;

import com.topov.forum.validation.constraint.UniqueEmail;
import com.topov.forum.validation.constraint.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotEmpty(message = "The username must not be empty")
    @UniqueUsername
    protected String username;
    @NotEmpty(message = "The password must not be empty")
    protected String password;
    @Email(message = "The email is incorrect")
    @NotEmpty(message = "The email must not be empty")
    @UniqueEmail
    protected String email;


}
