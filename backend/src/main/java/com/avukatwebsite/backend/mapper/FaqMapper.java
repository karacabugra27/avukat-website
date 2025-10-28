package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestFaq;
import com.avukatwebsite.backend.dto.response.ResponseFaq;
import com.avukatwebsite.backend.entity.Faq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FaqMapper {

    ResponseFaq toDto(Faq entity);

    @Mapping(target = "id", ignore = true)
    Faq toEntity(RequestFaq dto);

}
