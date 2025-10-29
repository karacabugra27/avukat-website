package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestMembership;
import com.avukatwebsite.backend.dto.response.ResponseMembership;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.Membership;
import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.MembershipMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.repository.MembershipRepository;
import com.avukatwebsite.backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;
    private final LawyerRepository lawyerRepository;


    @Override
    public ResponseMembership create(RequestMembership dto) {
        Lawyer lawyer = resolveLawyer(dto.getLawyerId());
        validateDateRange(dto.getStartDate(), dto.getEndDate());

        Membership entity = membershipMapper.toEntity(dto);
        entity.setLawyer(lawyer);

        Membership saved = membershipRepository.save(entity);
        return membershipMapper.toDto(saved);
    }

    @Override
    public List<ResponseMembership> getAll() {
        return membershipRepository.findAll()
                .stream()
                .map(membershipMapper::toDto)
                .toList();
    }

    @Override
    public ResponseMembership getById(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.MEMBERSHIP_NOT_FOUND,
                        "Üyelik bulunamadı: " + id));
        return membershipMapper.toDto(membership);
    }

    @Override
    public void delete(Long id) {
        if (!membershipRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ErrorType.MEMBERSHIP_NOT_FOUND,
                    "Üyelik bulunamadı: " + id);
        }
        membershipRepository.deleteById(id);
    }

    @Override
    public ResponseMembership update(Long id, RequestMembership dto) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.MEMBERSHIP_NOT_FOUND,
                        "Üyelik bulunamadı: " + id));

        if (dto.getLawyerId() != null && !dto.getLawyerId().equals(membership.getLawyer().getId())) {
            Lawyer lawyer = resolveLawyer(dto.getLawyerId());
            membership.setLawyer(lawyer);
        }

        if (dto.getName() != null) {
            membership.setName(dto.getName());
        }
        if (dto.getStartDate() != null) {
            membership.setStartDate(dto.getStartDate());
        }
        membership.setEndDate(dto.getEndDate());

        validateDateRange(membership.getStartDate(), membership.getEndDate());

        Membership saved = membershipRepository.save(membership);
        return membershipMapper.toDto(saved);
    }

    private Lawyer resolveLawyer(Long lawyerId) {
        if (lawyerId == null) {
            throw new BusinessException(ErrorType.GENERIC_BUSINESS_ERROR, "lawyerId alanı zorunludur");
        }
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.LAWYER_NOT_FOUND,
                        "Avukat bulunamadı: " + lawyerId));
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return;
        }
        if (endDate.isBefore(startDate)) {
            throw new BusinessException(ErrorType.MEMBERSHIP_INVALID_DATE_RANGE);
        }
    }
}
