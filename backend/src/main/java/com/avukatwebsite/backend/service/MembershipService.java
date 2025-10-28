package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestMembership;
import com.avukatwebsite.backend.dto.response.ResponseMembership;

import java.util.List;

public interface MembershipService {

    ResponseMembership create(RequestMembership dto);

    List<ResponseMembership> getAll();

    ResponseMembership getById(Long id);

    void delete(Long id);

    ResponseMembership update(Long id, RequestMembership dto);

}
