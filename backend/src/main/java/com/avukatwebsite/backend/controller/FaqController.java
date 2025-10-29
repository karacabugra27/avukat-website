package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestFaq;
import com.avukatwebsite.backend.dto.response.ResponseFaq;
import com.avukatwebsite.backend.service.FaqService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
@Validated
public class FaqController {
    private final FaqService faqService;

    @PostMapping
    public ResponseEntity<ResponseFaq> createFaq(@Valid @RequestBody RequestFaq request) {
        ResponseFaq created = faqService.createFaq(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseFaq>> getAllFaq() {
        return ResponseEntity.ok(faqService.getAllFaq());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseFaq> getFaqById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(faqService.getFaqById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseFaq> update(@Positive @PathVariable Long id, @Valid @RequestBody RequestFaq request) {
        return ResponseEntity.ok(faqService.update(id, request));
    }
}
