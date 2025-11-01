package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.AdminCreateLawyerRequest;
import com.avukatwebsite.backend.dto.request.UpdateOwnProfileRequest;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;

import java.util.List;

public interface LawyerService {
    ResponseLawyer create(AdminCreateLawyerRequest request);

    ResponseLawyer getById(Long id);

    List<ResponseLawyer> getAll();

    void delete(Long id);

    ResponseLawyerWithExperiences getLawyerWithExperiencesById(Long id);

    ResponseLawyer updateOwnProfile(String email, UpdateOwnProfileRequest request);

    void changeOwnPassword(String email, String currentPassword, String newPassword);

    void updatePassword(Long lawyerId, String newPassword);

    String resetPassword(Long lawyerId);
}
