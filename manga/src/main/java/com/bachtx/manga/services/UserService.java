package com.bachtx.manga.services;

import com.bachtx.manga.dto.request.LoginRequest;
import com.bachtx.manga.dto.request.RegisterRequest;
import com.bachtx.manga.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
}
