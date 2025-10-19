package com.avukatwebsite.backend.dto.response;

import com.avukatwebsite.backend.entity.Lawyer;
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

    private Lawyer fullName;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;



}
