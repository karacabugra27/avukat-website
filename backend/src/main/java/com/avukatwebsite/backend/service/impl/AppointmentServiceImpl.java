package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;
import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.AppointmentMapper;
import com.avukatwebsite.backend.repository.AppointmentRepository;
import com.avukatwebsite.backend.repository.LawyerScheduleRepository;
import com.avukatwebsite.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final LawyerScheduleRepository lawyerScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ResponseAppointment createAppointment(RequestAppointment dto) {
        validateTimeRange(dto.getStartTime(), dto.getEndTime());

        LawyerSchedule schedule = lawyerScheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.APPOINTMENT_SCHEDULE_NOT_FOUND,
                        "Çalışma takvimi bulunamadı: " + dto.getScheduleId()));

        ensureScheduleAllowsAppointment(schedule, dto.getStartTime(), dto.getEndTime());

        Appointment entity = appointmentMapper.toEntity(dto, schedule);
        entity.setSchedule(schedule);

        Appointment saved = appointmentRepository.save(entity);
        log.info("[traceId={}] Randevu oluşturuldu id={}, scheduleId={}, appointmentDate={}",
                traceId(), saved.getId(), schedule.getId(), saved.getAppointmentDate());
        return appointmentMapper.toDto(saved);
    }

    @Override
    public List<ResponseAppointment> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<ResponseAppointment> getByScheduleId(Long scheduleId) {
        return appointmentRepository.findByScheduleId(scheduleId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.APPOINTMENT_NOT_FOUND,
                        "Randevu bulunamadı: " + id));

        appointmentRepository.delete(appointment);
        log.info("[traceId={}] Randevu silindi id={}", traceId(), id);
    }

    @Override
    public ResponseAppointment update(Long id, RequestAppointment dto) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.APPOINTMENT_NOT_FOUND,
                        "Randevu bulunamadı: " + id));

        if (dto.getScheduleId() != null && !dto.getScheduleId().equals(entity.getSchedule().getId())) {
            LawyerSchedule schedule = lawyerScheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            ErrorType.APPOINTMENT_SCHEDULE_NOT_FOUND,
                            "Çalışma takvimi bulunamadı: " + dto.getScheduleId()));
            entity.setSchedule(schedule);
        }

        if (dto.getAppointmentDate() != null) {
            entity.setAppointmentDate(dto.getAppointmentDate());
        }
        if (dto.getStartTime() != null) {
            entity.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            entity.setEndTime(dto.getEndTime());
        }
        if (dto.getClientFirstName() != null) {
            entity.setClientFirstName(dto.getClientFirstName());
        }
        if (dto.getClientLastName() != null) {
            entity.setClientLastName(dto.getClientLastName());
        }
        if (dto.getClientPhone() != null) {
            entity.setClientPhone(dto.getClientPhone());
        }

        validateTimeRange(entity.getStartTime(), entity.getEndTime());
        ensureScheduleAllowsAppointment(entity.getSchedule(), entity.getStartTime(), entity.getEndTime());

        Appointment saved = appointmentRepository.save(entity);
        log.info("[traceId={}] Randevu güncellendi id={}, scheduleId={}",
                traceId(), id, entity.getSchedule().getId());
        return appointmentMapper.toDto(saved);
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (start == null || end == null) {
            return;
        }
        if (!end.isAfter(start)) {
            throw new BusinessException(ErrorType.APPOINTMENT_TIME_INVALID);
        }
    }

    private void ensureScheduleAllowsAppointment(LawyerSchedule schedule, LocalTime start, LocalTime end) {
        if (schedule.isDayOff()) {
            throw new BusinessException(ErrorType.APPOINTMENT_DAY_OFF);
        }
        if (start == null || end == null) {
            return;
        }
        LocalTime scheduleStart = schedule.getStartTime();
        LocalTime scheduleEnd = schedule.getEndTime();

        boolean startsBefore = scheduleStart != null && start.isBefore(scheduleStart);
        boolean endsAfter = scheduleEnd != null && end.isAfter(scheduleEnd);

        if (startsBefore || endsAfter) {
            throw new BusinessException(ErrorType.APPOINTMENT_TIME_INVALID,
                    "Randevu saati çalışma saatleri dışında olamaz.");
        }
    }

    private String traceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
