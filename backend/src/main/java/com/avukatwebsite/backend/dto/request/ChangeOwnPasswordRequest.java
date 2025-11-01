package com.avukatwebsite.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeOwnPasswordRequest(
    @NotBlank String currentPassword,
    @NotBlank @Size(min = 6, max = 64) String newPassword
) {
}
