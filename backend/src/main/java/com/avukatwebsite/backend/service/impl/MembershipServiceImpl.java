package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestMembership;
import com.avukatwebsite.backend.dto.response.ResponseMembership;
import com.avukatwebsite.backend.entity.Lawyer;
import com.avukatwebsite.backend.entity.Membership;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.MembershipMapper;
import com.avukatwebsite.backend.repository.LawyerRepository;
import com.avukatwebsite.backend.repository.MembershipRepository;
import com.avukatwebsite.backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found: " + id));
        return membershipMapper.toDto(membership);
    }

    @Override
    public void delete(Long id) {
        if (!membershipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membership not found: " + id);
        }
        membershipRepository.deleteById(id);
    }

    @Override
    public ResponseMembership update(Long id, RequestMembership dto) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found: " + id));

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

        Membership saved = membershipRepository.save(membership);
        return membershipMapper.toDto(saved);
    }

    private Lawyer resolveLawyer(Long lawyerId) {
        if (lawyerId == null) {
            throw new IllegalArgumentException("lawyerId is required");
        }
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found: " + lawyerId));
    }
}
