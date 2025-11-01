package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.AuthLoginRequest;
import com.avukatwebsite.backend.dto.response.AuthResponse;
import com.avukatwebsite.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
