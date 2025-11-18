package com.avukatwebsite.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAppointment {

    private Long id;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long lawyerId;

    private String lawyerFirstName;

    private String lawyerLastName;

    private String clientFirstName;

    private String clientLastName;

    private String clientPhone;



}
