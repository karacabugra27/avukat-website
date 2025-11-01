package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;
import com.avukatwebsite.backend.entity.Lawyer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ExperienceMapper.class})
public interface LawyerMapper {

    ResponseLawyer toDto(Lawyer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "experiences", ignore = true)
    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "lawyerSchedule", ignore = true)
    Lawyer toEntity(RequestLawyer dto);

    ResponseLawyerWithExperiences toExperienceDto(Lawyer entity);

}
