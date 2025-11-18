package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.AuthLoginRequest;
import com.avukatwebsite.backend.dto.response.AuthResponse;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.security.JwtService;
import com.avukatwebsite.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LawyerRepository lawyerRepository;

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Lawyer lawyer = lawyerRepository.findByEmail(request.email())
            .orElseThrow(() -> new BusinessException(ErrorType.AUTH_NOT_FOUND, "Kullanıcı bulunamadı"));

        String token = jwtService.generateToken(lawyer.getEmail(), lawyer.getRole());
        log.info("User {} logged in with authorities {}", lawyer.getEmail(), authentication.getAuthorities());

        return new AuthResponse(
            lawyer.getId(),
            token,
            jwtService.getExpiration(token),
            lawyer.getRole()
        );
    }
}
