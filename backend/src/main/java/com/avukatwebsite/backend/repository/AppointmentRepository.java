package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.Appointment;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByScheduleId(Long scheduleId);
}
