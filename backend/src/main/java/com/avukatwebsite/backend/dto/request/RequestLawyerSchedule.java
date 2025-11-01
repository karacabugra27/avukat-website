package com.avukatwebsite.backend.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLawyerSchedule {

    @NotNull
    private Long lawyerId;

    @NotNull
    private DayOfWeek dayOfWeek;

    @Nullable
    private LocalTime startTime;

    @Nullable
    private LocalTime endTime;

    private boolean dayOff;
}
