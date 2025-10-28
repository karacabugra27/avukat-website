package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestLawyerSchedule;
import com.avukatwebsite.backend.dto.response.ResponseLawyerSchedule;

import java.util.List;

public interface LawyerScheduleService {

    ResponseLawyerSchedule create(RequestLawyerSchedule dto);

    List<ResponseLawyerSchedule> getAll();

    List<ResponseLawyerSchedule> getByLawyerId(Long lawyerId);

    void delete(Long id);

    ResponseLawyerSchedule update(Long id, RequestLawyerSchedule dto);

    ResponseLawyerSchedule getById(Long id);



}
