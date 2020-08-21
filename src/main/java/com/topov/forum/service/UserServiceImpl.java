package com.topov.forum.service;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.topov.forum.model.Role.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addRegularUser(ForumUser newUser) {
        newUser.addRole(new Role(Roles.USER));
        userRepository.save(newUser);
    }

    @Override
    public void enableUser(String username) {
        userRepository.findByUsername(username)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user doesn't exist"))
                      .enable();
    }
}
