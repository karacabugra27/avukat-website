package com.avukatwebsite.backend.config;

import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.Role;
import com.avukatwebsite.backend.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final LawyerRepository lawyerRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedSuperAdmin() {
        return args -> {
            if (lawyerRepository.existsByEmail("admin@avukat.com")) {
                return;
            }
            Lawyer admin = new Lawyer();
            admin.setEmail("admin@avukat.com");
            admin.setRole(Role.SUPER_ADMIN);
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            lawyerRepository.save(admin);
            log.info("Super admin hesabı oluşturuldu (admin@avukat.com)");
        };
    }
}
