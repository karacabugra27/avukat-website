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

    private Long scheduleId;

    private Long lawyerId;

    private String firstName;

    private String lastName;



}
