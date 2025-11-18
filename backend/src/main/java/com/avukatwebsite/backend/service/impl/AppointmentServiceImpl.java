package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;
import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.AppointmentMapper;
import com.avukatwebsite.backend.repository.AppointmentRepository;
import com.avukatwebsite.backend.repository.LawyerScheduleRepository;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final LawyerScheduleRepository lawyerScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final LawyerRepository lawyerRepository;

    @Override
    public ResponseAppointment createAppointment(RequestAppointment dto) {
        validateTimeRange(dto.getStartTime(), dto.getEndTime());

        Lawyer lawyer = findLawyer(dto.getLawyerId());
        LawyerSchedule schedule = findScheduleForDate(lawyer.getId(), dto.getAppointmentDate());
        ensureScheduleAllowsAppointment(schedule, dto.getStartTime(), dto.getEndTime());

        Appointment entity = appointmentMapper.toEntity(dto, lawyer);
        Appointment saved = appointmentRepository.save(entity);
        log.info("[traceId={}] Randevu oluşturuldu id={}, lawyerId={}, appointmentDate={}",
                traceId(), saved.getId(), lawyer.getId(), saved.getAppointmentDate());
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
    public List<ResponseAppointment> getByLawyerId(Long lawyerId) {
        return appointmentRepository.findByLawyerId(lawyerId)
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

        if (dto.getLawyerId() != null && !dto.getLawyerId().equals(entity.getLawyer().getId())) {
            Lawyer lawyer = findLawyer(dto.getLawyerId());
            entity.setLawyer(lawyer);
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
        LawyerSchedule schedule = findScheduleForDate(entity.getLawyer().getId(), entity.getAppointmentDate());
        ensureScheduleAllowsAppointment(schedule, entity.getStartTime(), entity.getEndTime());

        Appointment saved = appointmentRepository.save(entity);
        log.info("[traceId={}] Randevu güncellendi id={}, lawyerId={}",
                traceId(), id, entity.getLawyer().getId());
        return appointmentMapper.toDto(saved);
    }

    private Lawyer findLawyer(Long lawyerId) {
        if (lawyerId == null) {
            throw new BusinessException(ErrorType.GENERIC_BUSINESS_ERROR, "Avukat seçilmelidir");
        }
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + lawyerId));
    }

    private LawyerSchedule findScheduleForDate(Long lawyerId, LocalDate appointmentDate) {
        if (appointmentDate == null) {
            throw new BusinessException(ErrorType.GENERIC_BUSINESS_ERROR, "Randevu tarihi seçilmelidir");
        }
        return lawyerScheduleRepository.findByLawyerIdAndDayOfWeek(lawyerId, appointmentDate.getDayOfWeek())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.APPOINTMENT_SCHEDULE_NOT_FOUND,
                        "Çalışma takvimi bulunamadı: " + appointmentDate.getDayOfWeek()));
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
