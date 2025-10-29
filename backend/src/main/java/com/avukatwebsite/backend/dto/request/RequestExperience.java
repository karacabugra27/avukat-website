package com.avukatwebsite.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestExperience {

    @NotNull
    private Long lawyerId;

    @NotBlank
    @Size(min = 3,max = 30)
    private String title;

    @NotBlank
    private String description;

}
