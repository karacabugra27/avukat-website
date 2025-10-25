package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.LawyerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerScheduleRepository extends JpaRepository<LawyerSchedule, Long> {
    List<LawyerSchedule> findByLawyerId(Long lawyerId);
}
