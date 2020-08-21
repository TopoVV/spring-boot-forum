package com.topov.forum.dto.request;

import com.topov.forum.validation.constraint.UniqueEmail;
import com.topov.forum.validation.constraint.UniqueUsername;
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
    @UniqueUsername
    private String username;
    @Email(message = "The email is incorrect")
    @NotEmpty(message = "The email must not be empty")
    @UniqueEmail
    private String email;
}
