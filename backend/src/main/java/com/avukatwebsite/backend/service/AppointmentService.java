package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;

import java.util.List;

public interface AppointmentService {

    ResponseAppointment createAppointment(RequestAppointment dto);

    List<ResponseAppointment> getAllAppointments();

    List<ResponseAppointment> getByScheduleId(Long scheduleId);

    void delete(Long id);

    ResponseAppointment update(Long id, RequestAppointment dto);

}
