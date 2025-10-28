package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;
import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.mapper.AppointmentMapper;
import com.avukatwebsite.backend.repository.AppointmentRepository;
import com.avukatwebsite.backend.repository.LawyerScheduleRepository;
import com.avukatwebsite.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final LawyerScheduleRepository lawyerScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ResponseAppointment createAppointment(RequestAppointment dto) {
        LawyerSchedule schedule = lawyerScheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule bulunamadı"));

        Appointment entity = appointmentMapper.toEntity(dto, schedule);
        entity.setSchedule(schedule);

        Appointment saved = appointmentRepository.save(entity);
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
        Optional<Appointment> isExist = appointmentRepository.findById(id);
        if(isExist.isPresent()){
            appointmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("id bulunamadı");
        }
    }

    @Override
    public ResponseAppointment update(Long id, RequestAppointment dto) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow();

        if (dto.getScheduleId() != null && !entity.getSchedule().getId().equals(dto.getScheduleId())) {
            LawyerSchedule schedule = lawyerScheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found: " + dto.getScheduleId()));
            entity.setSchedule(schedule);
        }

        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setClientFirstName(dto.getClientFirstName());
        entity.setClientLastName(dto.getClientLastName());
        entity.setClientPhone(dto.getClientPhone());

        appointmentRepository.save(entity);

        return appointmentMapper.toDto(entity);
    }


}
