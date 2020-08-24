package com.topov.forum.controller;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public void login(LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final ForumUser user = userRepository.findByUsernameWithRoles(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));

    }
}
