package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;

import java.util.List;

public interface LawyerService {
    ResponseLawyer create(RequestLawyer dto);

    ResponseLawyer getById(Long id);

    List<ResponseLawyer> getAll();

    void delete(Long id);

    ResponseLawyer update(Long id, RequestLawyer dto);

    ResponseLawyerWithExperiences getLawyerWithExperiencesById(Long id);
}
