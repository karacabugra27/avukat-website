package com.avukatwebsite.backend.mapper;

import com.avukatwebsite.backend.dto.request.RequestMembership;
import com.avukatwebsite.backend.dto.response.ResponseMembership;
import com.avukatwebsite.backend.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MembershipMapper {

    @Mapping(source = "lawyer.firstName", target = "firstName")
    @Mapping(source = "lawyer.lastName", target = "lastName")
    ResponseMembership toDto(Membership entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "lawyerId", target = "lawyer.id")
    Membership toEntity(RequestMembership dto);
}
