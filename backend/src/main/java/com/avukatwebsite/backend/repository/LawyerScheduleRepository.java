package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.LawyerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerScheduleRepository extends JpaRepository<LawyerSchedule, Long> {
    List<LawyerSchedule> findByLawyerId(Long lawyerId);

    Optional<LawyerSchedule> findByLawyerIdAndDayOfWeek(Long lawyerId, DayOfWeek dayOfWeek);
}
