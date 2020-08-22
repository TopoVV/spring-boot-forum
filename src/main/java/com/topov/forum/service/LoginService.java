package com.topov.forum.service;

import com.topov.forum.dto.request.LoginRequest;

public interface LoginService {
    void login(LoginRequest loginRequest);
}
