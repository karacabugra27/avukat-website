package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.RequestContactMessage;
import com.avukatwebsite.backend.dto.response.ResponseContactMessage;
import com.avukatwebsite.backend.entity.ContactMessage;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.ContactMessageMapper;
import com.avukatwebsite.backend.repository.ContactMessageRepository;
import com.avukatwebsite.backend.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    @Override
    public ResponseContactMessage create(RequestContactMessage dto) {
        ContactMessage entity = contactMessageMapper.toEntity(dto);
        entity.setCreatedAt(Instant.now());

        ContactMessage saved = contactMessageRepository.save(entity);
        log.info("[traceId={}] İletişim mesajı oluşturuldu id={}, topic={}",
                traceId(), saved.getId(), saved.getTopic());
        return contactMessageMapper.toDto(saved);
    }

    @Override
    public List<ResponseContactMessage> getAll() {
        return contactMessageRepository.findAll()
                .stream()
                .map(contactMessageMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ErrorType.CONTACT_MESSAGE_NOT_FOUND,
                    "İletişim mesajı bulunamadı: " + id);
        }
        contactMessageRepository.deleteById(id);
        log.info("[traceId={}] İletişim mesajı silindi id={}", traceId(), id);
    }

    private String traceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
