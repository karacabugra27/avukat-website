package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.AuthLoginRequest;
import com.avukatwebsite.backend.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthLoginRequest request);
}
