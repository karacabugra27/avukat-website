package com.avukatwebsite.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLawyerSchedule {

    private Long id;

    private Long lawyerId;

    private String firstName;

    private String lastName;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean dayOff;

    private List<ResponseAppointment> appointments;

}
