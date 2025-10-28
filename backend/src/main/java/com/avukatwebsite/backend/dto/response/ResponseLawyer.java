package com.avukatwebsite.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLawyer {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String bio;


}
