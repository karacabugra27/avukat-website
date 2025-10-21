package com.avukatwebsite.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAppointment {

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

}
