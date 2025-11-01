package com.avukatwebsite.backend.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMembership {

    @NotNull
    private Long lawyerId;

    @NotBlank
    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    private LocalDate endDate;

}
