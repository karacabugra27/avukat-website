package com.avukatwebsite.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminUpdateLawyerPasswordRequest(
    @NotBlank @Size(min = 6, max = 64) String newPassword
) {
}
