package com.avukatwebsite.backend.dto.response;

import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.Lawyer;
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

    private Lawyer fullName;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isDayOff;

    private List<Appointment> appointments;

}
