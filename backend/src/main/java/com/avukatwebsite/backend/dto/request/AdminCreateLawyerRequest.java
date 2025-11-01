package com.avukatwebsite.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminCreateLawyerRequest(
    @NotBlank @Email String email
) {
}
