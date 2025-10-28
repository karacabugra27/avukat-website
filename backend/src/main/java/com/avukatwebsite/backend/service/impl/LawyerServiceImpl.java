package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.LawyerMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.service.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {

    private final LawyerRepository lawyerRepository;
    private final LawyerMapper lawyerMapper;

    @Override
    @Transactional
    public ResponseLawyer create(RequestLawyer dto) {
        if (dto.getEmail() != null && lawyerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already in use: " + dto.getEmail());
        }

        Lawyer entity = lawyerMapper.toEntity(dto);
        entity.setId(null);
        Lawyer saved = lawyerRepository.save(entity);
        return lawyerMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseLawyer getById(Long id) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found: " + id));
        return lawyerMapper.toDto(lawyer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseLawyer> getAll() {
        return lawyerRepository.findAll()
                .stream()
                .map(lawyerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseLawyer update(Long id, RequestLawyer dto) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found: " + id));

        if (dto.getEmail() != null && !dto.getEmail().equals(lawyer.getEmail())
                && lawyerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already in use: " + dto.getEmail());
        }

        applyUpdates(lawyer, dto);
        Lawyer saved = lawyerRepository.save(lawyer);
        return lawyerMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!lawyerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lawyer not found: " + id);
        }
        lawyerRepository.deleteById(id);
    }

    private void applyUpdates(Lawyer target, RequestLawyer source) {
        if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        }
        if (source.getLastName() != null) {
            target.setLastName(source.getLastName());
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getBio() != null) {
            target.setBio(source.getBio());
        }
    }
}
