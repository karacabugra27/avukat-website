package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;
import com.avukatwebsite.backend.entity.Lawyer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ExperienceMapper.class})
public interface LawyerMapper {

    ResponseLawyer toDto(Lawyer entity);

    ResponseLawyerWithExperiences toExperienceDto(Lawyer entity);

}
