package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestContactMessage;
import com.avukatwebsite.backend.dto.response.ResponseContactMessage;
import com.avukatwebsite.backend.entity.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ContactMessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ContactMessage toEntity(RequestContactMessage dto);

    ResponseContactMessage toDto(ContactMessage entity);

}
