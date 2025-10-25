package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestExperience;
import com.avukatwebsite.backend.dto.response.ResponseExperience;

import java.util.List;

public interface ExperienceService {

    ResponseExperience createExperience(RequestExperience dto);

    //lawyer id'ye göre experience getirme
    List<ResponseExperience> getExperienceByLawyerId(Long lawyerId);

    //title'a göre getirme
    List<ResponseExperience> getExperienceByTitle(String title);

    //experince silme
    void delete(Long id);

    ResponseExperience update(Long id, RequestExperience dto);


}
