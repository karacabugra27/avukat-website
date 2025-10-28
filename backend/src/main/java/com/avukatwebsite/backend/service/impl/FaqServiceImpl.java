package com.avukatwebsite.backend.service.impl;

import com.avukatwebsite.backend.dto.request.RequestFaq;
import com.avukatwebsite.backend.dto.response.ResponseFaq;
import com.avukatwebsite.backend.entity.Faq;
import com.avukatwebsite.backend.mapper.FaqMapper;
import com.avukatwebsite.backend.repository.FaqRepository;
import com.avukatwebsite.backend.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;


    @Override
    public ResponseFaq createFaq(RequestFaq dto) {
        Faq entity = faqMapper.toEntity(dto);
        Faq saved = faqRepository.save(entity);
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
                .orElseThrow(() -> new RuntimeException("FAQ not found: " + id));
        return faqMapper.toDto(faq);
    }

    @Override
    public void delete(Long id) {
        Optional<Faq> isExist = faqRepository.findById(id);
        if (isExist.isPresent()) {
            faqRepository.deleteById(id);
        } else {
            throw new RuntimeException("bu id'de faq yok");
        }
    }

    @Override
    public ResponseFaq update(Long id, RequestFaq dto) {
        Faq entity = faqRepository.findById(id).orElseThrow();
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());

        faqRepository.save(entity);

        return faqMapper.toDto(entity);
    }
}
