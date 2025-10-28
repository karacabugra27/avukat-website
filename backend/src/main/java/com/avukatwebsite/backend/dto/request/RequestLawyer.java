package com.avukatwebsite.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLawyer {

    private String firstName;

    private String lastName;

    private String email;

    private String bio;

}
