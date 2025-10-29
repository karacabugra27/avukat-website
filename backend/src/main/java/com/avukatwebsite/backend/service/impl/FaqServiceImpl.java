package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.config.TraceIdFilter;
import com.avukatwebsite.backend.dto.request.RequestFaq;
import com.avukatwebsite.backend.dto.response.ResponseFaq;
import com.avukatwebsite.backend.entity.Faq;
import com.avukatwebsite.backend.exception.ErrorType;
import com.avukatwebsite.backend.exception.ResourceNotFoundException;
import com.avukatwebsite.backend.mapper.FaqMapper;
import com.avukatwebsite.backend.repository.FaqRepository;
import com.avukatwebsite.backend.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;


    @Override
    public ResponseFaq createFaq(RequestFaq dto) {
        Faq entity = faqMapper.toEntity(dto);
        Faq saved = faqRepository.save(entity);
        log.info("[traceId={}] SSS oluşturuldu id={}", traceId(), saved.getId());
        return faqMapper.toDto(saved);
    }

    @Override
    public List<ResponseFaq> getAllFaq() {
        return faqRepository.findAll()
                .stream()
                .map(faqMapper::toDto)
                .toList();
    }

    @Override
    public ResponseFaq getFaqById(Long id) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.FAQ_NOT_FOUND,
                        "SSS kaydı bulunamadı: " + id));
        return faqMapper.toDto(faq);
    }

    @Override
    public void delete(Long id) {
        Optional<Faq> isExist = faqRepository.findById(id);
        if (isExist.isPresent()) {
            faqRepository.deleteById(id);
            log.info("[traceId={}] SSS silindi id={}", traceId(), id);
        } else {
            throw new ResourceNotFoundException(
                    ErrorType.FAQ_NOT_FOUND,
                    "SSS kaydı bulunamadı: " + id);
        }
    }

    @Override
    public ResponseFaq update(Long id, RequestFaq dto) {
        Faq entity = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorType.FAQ_NOT_FOUND,
                        "SSS kaydı bulunamadı: " + id));
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());

        faqRepository.save(entity);
        log.info("[traceId={}] SSS güncellendi id={}", traceId(), id);

        return faqMapper.toDto(entity);
    }

    private String traceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
