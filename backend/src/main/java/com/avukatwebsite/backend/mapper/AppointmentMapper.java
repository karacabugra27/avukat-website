package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;
import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AppointmentMapper {

    @Mapping(target = "scheduleId", source = "schedule.id")
    @Mapping(target = "lawyerId", source = "schedule.lawyer.id")
    @Mapping(target = "lawyerFirstName", source = "schedule.lawyer.firstName")
    @Mapping(target = "lawyerLastName", source = "schedule.lawyer.lastName")
    ResponseAppointment toDto(Appointment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schedule", source = "schedule")
    @Mapping(target = "appointmentDate", source = "dto.appointmentDate")
    @Mapping(target = "startTime", source = "dto.startTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "clientFirstName", source = "dto.clientFirstName")
    @Mapping(target = "clientLastName", source = "dto.clientLastName")
    @Mapping(target = "clientPhone", source = "dto.clientPhone")
    Appointment toEntity(RequestAppointment dto, LawyerSchedule schedule);

}
