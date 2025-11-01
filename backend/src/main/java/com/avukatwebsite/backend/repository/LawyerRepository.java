package com.avukatwebsite.backend.repository;

import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    boolean existsByEmail(String email);
    Optional<Lawyer> findByEmail(String email);
    List<Lawyer> findAllByRole(Role role);
}
