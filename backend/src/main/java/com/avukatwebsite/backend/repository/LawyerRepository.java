package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    boolean existsByEmail(String email);
}
