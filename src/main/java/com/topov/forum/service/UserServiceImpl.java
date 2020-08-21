package com.topov.forum.service;

import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.ForumUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
