package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestExperience;
import com.avukatwebsite.backend.dto.response.ResponseExperience;
import com.avukatwebsite.backend.entity.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExperienceMapper {

    ResponseExperience toDto(Experience entity);

    @Mapping(target = "id",ignore = true)
    @Mapping(source = "lawyerId", target = "lawyer.id")
    Experience toEntity(RequestExperience dto);
}
