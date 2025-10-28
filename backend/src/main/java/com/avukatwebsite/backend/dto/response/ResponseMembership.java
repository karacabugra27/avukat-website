package com.avukatwebsite.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMembership {

    private Long id;

    private String firstName;

    private String lastName;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;


}
