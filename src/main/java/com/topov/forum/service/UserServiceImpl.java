package com.topov.forum.service;

import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.ForumUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    private final ForumUserRepository userRepository;

    @Autowired
    public UserServiceImpl(ForumUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(ForumUser user) {
        userRepository.save(user);
    }

    @Override
    public void enableUser(String username) {
        userRepository.findByUsername(username)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user doesn't exist"))
                      .enable();
    }
}
