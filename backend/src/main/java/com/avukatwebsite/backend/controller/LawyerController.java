package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;
import com.avukatwebsite.backend.service.LawyerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawyers")
@RequiredArgsConstructor
@Validated
public class LawyerController {
    private final LawyerService lawyerService;

    @PostMapping
    public ResponseEntity<ResponseLawyer> create(@Valid @RequestBody RequestLawyer request) {
        ResponseLawyer createdLawyer = lawyerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLawyer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLawyer> getById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(lawyerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseLawyer>> getAll() {
        return ResponseEntity.ok(lawyerService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseLawyer> update(@Positive @PathVariable Long id, @Valid @RequestBody RequestLawyer request) {
        return ResponseEntity.ok(lawyerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        lawyerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/experiences/{id}")
    public ResponseEntity<ResponseLawyerWithExperiences> getLawyerExperiencesById(@PathVariable Long id){
        return ResponseEntity.ok(lawyerService.getLawyerWithExperiencesById(id));
    }
}
