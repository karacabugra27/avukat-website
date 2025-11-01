package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.AdminCreateLawyerRequest;
import com.avukatwebsite.backend.dto.request.UpdateOwnProfileRequest;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.Role;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.LawyerMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.service.LawyerService;
import com.avukatwebsite.backend.service.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LawyerServiceImpl implements LawyerService {

    private static final String DEFAULT_PASSWORD = "123";
    private static final String TEMP_PASSWORD_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
    private static final int TEMP_PASSWORD_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PROFILE_IMAGE_DIRECTORY = "lawyers/profile-images";

    private final LawyerRepository lawyerRepository;
    private final LawyerMapper lawyerMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Override
    public ResponseLawyer create(AdminCreateLawyerRequest request) {
        if (lawyerRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorType.LAWYER_EMAIL_IN_USE,
                    "Bu e-posta adresi zaten kullanımda: " + request.email());
        }

        Lawyer lawyer = new Lawyer();
        lawyer.setEmail(request.email());
        lawyer.setRole(Role.LAWYER);
        lawyer.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));

        Lawyer saved = lawyerRepository.save(lawyer);
        log.info("[traceId={}] Avukat oluşturuldu id={}, email={}", traceId(), saved.getId(), saved.getEmail());
        return lawyerMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseLawyer getById(Long id) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + id
                ));
        return lawyerMapper.toDto(lawyer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseLawyer> getAll() {
        return lawyerRepository.findAllByRole(Role.LAWYER)
                .stream()
                .map(lawyerMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!lawyerRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ErrorType.LAWYER_NOT_FOUND,
                    "Avukat bulunamadı: " + id
            );
        }
        lawyerRepository.deleteById(id);
        log.info("[traceId={}] Avukat silindi id={}", traceId(), id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseLawyerWithExperiences getLawyerWithExperiencesById(Long id) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + id
                ));
        return lawyerMapper.toExperienceDto(lawyer);
    }

    @Override
    public List<ResponseLawyerWithExperiences> getLawyerWithExperiencesAll() {
        return lawyerRepository.findAllByRole(Role.LAWYER)
                .stream()
                .map(lawyerMapper::toExperienceDto)
                .toList();
    }

    @Override
    public ResponseLawyer updateOwnProfile(String email, UpdateOwnProfileRequest request) {
        Lawyer lawyer = lawyerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + email
                ));

        lawyer.setFirstName(request.firstName());
        lawyer.setLastName(request.lastName());
        lawyer.setBio(request.bio());

        log.info("[traceId={}] Avukat profili güncellendi email={}", traceId(), email);
        return lawyerMapper.toDto(lawyer);
    }

    @Override
    public void changeOwnPassword(String email, String currentPassword, String newPassword) {
        Lawyer lawyer = lawyerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + email
                ));

        if (!passwordEncoder.matches(currentPassword, lawyer.getPassword())) {
            throw new BusinessException(ErrorType.AUTH_BAD_CREDENTIALS, "Mevcut şifre yanlış");
        }

        lawyer.setPassword(passwordEncoder.encode(newPassword));
        log.info("[traceId={}] Avukat şifre değiştirdi email={}", traceId(), email);
    }

    @Override
    public void updatePassword(Long lawyerId, String newPassword) {
        Lawyer lawyer = lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + lawyerId
                ));
        lawyer.setPassword(passwordEncoder.encode(newPassword));
        log.info("[traceId={}] Avukat şifresi admin tarafından güncellendi id={}", traceId(), lawyerId);
    }

    @Override
    public String resetPassword(Long lawyerId) {
        Lawyer lawyer = lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + lawyerId
                ));

        String temporaryPassword = generateTemporaryPassword();
        lawyer.setPassword(passwordEncoder.encode(temporaryPassword));
        log.info("[traceId={}] Avukat şifresi sıfırlandı id={}", traceId(), lawyerId);
        return temporaryPassword;
    }

    @Override
    public ResponseLawyer updateProfileImage(String email, MultipartFile file) {
        Lawyer lawyer = lawyerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + email
                ));

        String newPath = fileStorageService.store(file, PROFILE_IMAGE_DIRECTORY);
        String oldPath = lawyer.getProfileImageUrl();

        lawyer.setProfileImageUrl(newPath);
        if (oldPath != null && !oldPath.equals(newPath)) {
            fileStorageService.delete(oldPath);
        }

        log.info("[traceId={}] Avukat profil fotoğrafı güncellendi email={}", traceId(), email);
        return lawyerMapper.toDto(lawyer);
    }

    @Override
    public void deleteProfileImage(String email) {
        Lawyer lawyer = lawyerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + email
                ));

        String oldPath = lawyer.getProfileImageUrl();
        if (oldPath != null && !oldPath.isBlank()) {
            fileStorageService.delete(oldPath);
            lawyer.setProfileImageUrl(null);
            log.info("[traceId={}] Avukat profil fotoğrafı silindi email={}", traceId(), email);
        }
    }

    private String generateTemporaryPassword() {
        StringBuilder builder = new StringBuilder(TEMP_PASSWORD_LENGTH);
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(TEMP_PASSWORD_CHARS.length());
            builder.append(TEMP_PASSWORD_CHARS.charAt(index));
        }
        return builder.toString();
    }

    private String traceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
