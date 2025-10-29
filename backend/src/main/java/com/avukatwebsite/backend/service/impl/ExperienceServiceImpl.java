package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestExperience;
import com.avukatwebsite.backend.dto.response.ResponseExperience;
import com.avukatwebsite.backend.entity.Experience;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.ExperienceMapper;
import com.avukatwebsite.backend.repository.ExperienceRepository;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceMapper experienceMapper;
    private final ExperienceRepository experienceRepository;
    private final LawyerRepository lawyerRepository;

    @Override
    public ResponseExperience createExperience(RequestExperience dto) {
        Lawyer lawyer = lawyerRepository.findById(dto.getLawyerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + dto.getLawyerId()));

        Experience entity = experienceMapper.toEntity(dto);
        entity.setLawyer(lawyer);

        Experience saved = experienceRepository.save(entity);
        return experienceMapper.toDto(saved);
    }

    @Override
    public List<ResponseExperience> getExperienceByLawyerId(Long lawyerId) {
        return experienceRepository.findByLawyerId(lawyerId)
                .stream()
                .map(experienceMapper::toDto)
                .toList();
    }

    @Override
    public List<ResponseExperience> getExperienceByTitle(String title) {
        return experienceRepository.findByTitle(title)
                .stream()
                .map(experienceMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Experience> isExist = experienceRepository.findById(id);
        if (isExist.isPresent()) {
            experienceRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(
                    ErrorType.EXPERIENCE_NOT_FOUND,
                    "Deneyim bulunamadı: " + id);
        }
    }

    @Override
    public ResponseExperience update(Long id, RequestExperience dto) {
        Experience entity = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.EXPERIENCE_NOT_FOUND,
                        "Deneyim bulunamadı: " + id));

        if(!entity.getLawyer().getId().equals(dto.getLawyerId())){
            Lawyer lawyer = lawyerRepository.findById(dto.getLawyerId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            ErrorType.LAWYER_NOT_FOUND,
                            "Avukat bulunamadı: " + dto.getLawyerId()));
            entity.setLawyer(lawyer);
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        experienceRepository.save(entity);

        return experienceMapper.toDto(entity);
    }


}
