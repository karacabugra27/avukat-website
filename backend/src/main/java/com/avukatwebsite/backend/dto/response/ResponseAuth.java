package com.avukatwebsite.backend.dto.response;

import com.avukatwebsite.backend.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuth {

    @NotBlank
    private String accessToken;

    @NotNull
    private Instant expiresDate;

    @NotBlank
    private Role role;
}
