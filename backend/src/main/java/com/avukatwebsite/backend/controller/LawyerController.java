package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.dto.response.ResponseLawyerWithExperiences;
import com.avukatwebsite.backend.service.LawyerService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLawyer> getById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(lawyerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseLawyer>> getAll() {
        return ResponseEntity.ok(lawyerService.getAll());
    }

    @GetMapping("/experiences/{id}")
    public ResponseEntity<ResponseLawyerWithExperiences> getLawyerExperiencesById(@PathVariable Long id){
        return ResponseEntity.ok(lawyerService.getLawyerWithExperiencesById(id));
    }
}
