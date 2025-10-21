package com.avukatwebsite.backend.dto.request;

import com.avukatwebsite.backend.entity.Lawyer;
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

    private Long lawyerId;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

}
