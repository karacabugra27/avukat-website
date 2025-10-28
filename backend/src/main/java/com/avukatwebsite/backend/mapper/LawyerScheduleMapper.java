package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestLawyerSchedule;
import com.avukatwebsite.backend.dto.response.ResponseLawyerSchedule;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = AppointmentMapper.class)
public interface LawyerScheduleMapper {

    @Mapping(source = "lawyer.id", target = "lawyerId")
    @Mapping(source = "lawyer.firstName", target = "firstName")
    @Mapping(source = "lawyer.lastName", target = "lastName")
    ResponseLawyerSchedule toDto(LawyerSchedule entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "lawyerId", target = "lawyer.id")
    @Mapping(target = "appointments", ignore = true)
    LawyerSchedule toEntity(RequestLawyerSchedule dto);

}
