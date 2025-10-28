package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByTitle(String title);
    List<Experience> findByLawyerId(Long lawyerId);
}
