package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestLawyerSchedule;
import com.avukatwebsite.backend.dto.response.ResponseLawyerSchedule;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.LawyerScheduleMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.repository.LawyerScheduleRepository;
import com.avukatwebsite.backend.service.LawyerScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LawyerScheduleServiceImpl implements LawyerScheduleService {

    private final LawyerScheduleMapper lawyerScheduleMapper;
    private final LawyerScheduleRepository lawyerScheduleRepository;
    private final LawyerRepository lawyerRepository;

    @Override
    @Transactional
    public ResponseLawyerSchedule create(RequestLawyerSchedule dto) {
        Lawyer lawyer = findLawyer(dto.getLawyerId());

        LawyerSchedule entity = lawyerScheduleMapper.toEntity(dto);
        entity.setLawyer(lawyer);
        entity.setAppointments(new ArrayList<>());

        validateTimeRange(entity.getStartTime(), entity.getEndTime());

        LawyerSchedule saved = lawyerScheduleRepository.save(entity);
        return lawyerScheduleMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseLawyerSchedule> getAll() {
        return lawyerScheduleRepository.findAll()
                .stream()
                .map(lawyerScheduleMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseLawyerSchedule> getByLawyerId(Long lawyerId) {
        return lawyerScheduleRepository.findByLawyerId(lawyerId)
                .stream()
                .map(lawyerScheduleMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!lawyerScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lawyer schedule not found: " + id);
        }
        lawyerScheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseLawyerSchedule update(Long id, RequestLawyerSchedule dto) {
        LawyerSchedule schedule = lawyerScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer schedule not found: " + id));

        if (dto.getLawyerId() != null && !dto.getLawyerId().equals(schedule.getLawyer().getId())) {
            Lawyer lawyer = findLawyer(dto.getLawyerId());
            schedule.setLawyer(lawyer);
        }

        if (dto.getDayOfWeek() != null) {
            schedule.setDayOfWeek(dto.getDayOfWeek());
        }
        if (dto.getStartTime() != null) {
            schedule.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            schedule.setEndTime(dto.getEndTime());
        }
        schedule.setDayOff(dto.isDayOff());

        validateTimeRange(schedule.getStartTime(), schedule.getEndTime());

        LawyerSchedule saved = lawyerScheduleRepository.save(schedule);
        return lawyerScheduleMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseLawyerSchedule getById(Long id) {
        LawyerSchedule schedule = lawyerScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer schedule not found: " + id));
        return lawyerScheduleMapper.toDto(schedule);
    }

    private Lawyer findLawyer(Long lawyerId) {
        if (lawyerId == null) {
            throw new IllegalArgumentException("lawyerId is required");
        }
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found: " + lawyerId));
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (start == null || end == null) {
            return;
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
    }
}
