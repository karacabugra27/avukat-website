package com.avukatwebsite.backend.dto.response;

import com.avukatwebsite.backend.entity.Role;

import java.time.Instant;

public record AuthResponse(
    String accessToken,
    Instant expiresAt,
    Role role
) {
}
