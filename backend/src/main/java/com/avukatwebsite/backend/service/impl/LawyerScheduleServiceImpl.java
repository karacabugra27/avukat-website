package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.RequestLawyerSchedule;
import com.avukatwebsite.backend.dto.response.ResponseLawyerSchedule;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.LawyerScheduleMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.repository.LawyerScheduleRepository;
import com.avukatwebsite.backend.service.LawyerScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        validateTimeRange(entity.getStartTime(), entity.getEndTime(), entity.isDayOff());

        LawyerSchedule saved = lawyerScheduleRepository.save(entity);
        log.info("[traceId={}] Çalışma takvimi oluşturuldu id={}, lawyerId={}, dayOff={}",
                traceId(), saved.getId(), saved.getLawyer().getId(), saved.isDayOff());
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
            throw new ResourceNotFoundException(
                    ErrorType.LAWYER_SCHEDULE_NOT_FOUND,
                    "Çalışma takvimi bulunamadı: " + id);
        }
        lawyerScheduleRepository.deleteById(id);
        log.info("[traceId={}] Çalışma takvimi silindi id={}", traceId(), id);
    }

    @Override
    @Transactional
    public ResponseLawyerSchedule update(Long id, RequestLawyerSchedule dto) {
        LawyerSchedule schedule = lawyerScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_SCHEDULE_NOT_FOUND,
                        "Çalışma takvimi bulunamadı: " + id));

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

        validateTimeRange(schedule.getStartTime(), schedule.getEndTime(), schedule.isDayOff());

        LawyerSchedule saved = lawyerScheduleRepository.save(schedule);
        log.info("[traceId={}] Çalışma takvimi güncellendi id={}, dayOff={}",
                traceId(), id, saved.isDayOff());
        return lawyerScheduleMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseLawyerSchedule getById(Long id) {
        LawyerSchedule schedule = lawyerScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_SCHEDULE_NOT_FOUND,
                        "Çalışma takvimi bulunamadı: " + id));
        return lawyerScheduleMapper.toDto(schedule);
    }

    private Lawyer findLawyer(Long lawyerId) {
        if (lawyerId == null) {
            throw new BusinessException(ErrorType.GENERIC_BUSINESS_ERROR, "lawyerId alanı zorunludur");
        }
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + lawyerId));
    }

    private void validateTimeRange(LocalTime start, LocalTime end, boolean dayOff) {
        if (dayOff) {
            return;
        }
        if (start == null || end == null) {
            throw new BusinessException(ErrorType.LAWYER_SCHEDULE_INVALID_TIME_RANGE,
                    "Tatil olmayan günlerde çalışma saatleri dolu olmalıdır");
        }
        if (!start.isBefore(end)) {
            throw new BusinessException(ErrorType.LAWYER_SCHEDULE_INVALID_TIME_RANGE);
        }
    }

    private String traceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
