package com.avukatwebsite.backend.security;

import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LawyerRepository lawyerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Lawyer lawyer = lawyerRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));

        return User.withUsername(lawyer.getEmail())
            .password(lawyer.getPassword())
            .roles(lawyer.getRole().name())
            .build();
    }
}
