package com.avukatwebsite.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLawyer {

    @NotBlank
    @Size(max = 50,min = 3)
    private String firstName;

    @NotBlank
    @Size(max = 20,min = 2)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String bio;

}
