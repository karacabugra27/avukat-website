package com.avukatwebsite.backend.controller;


import com.avukatwebsite.backend.dto.request.RequestExperience;
import com.avukatwebsite.backend.dto.response.ResponseExperience;
import com.avukatwebsite.backend.service.ExperienceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
@RequiredArgsConstructor
@Validated
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ResponseExperience> createExperience(@Valid @RequestBody RequestExperience request) {
        ResponseExperience created = experienceService.createExperience(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<ResponseExperience>> getExperienceByTitle(@PathVariable String title) {
        return ResponseEntity.ok(experienceService.getExperienceByTitle(title));
    }

    @GetMapping("/lawyer/{lawyerId}")
    public ResponseEntity<List<ResponseExperience>> getExperienceByLawyerId(@PathVariable Long lawyerId) {
        return ResponseEntity.ok(experienceService.getExperienceByLawyerId(lawyerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseExperience> update(@Positive @PathVariable Long id, @Valid @RequestBody RequestExperience request) {
        return ResponseEntity.ok(experienceService.update(id, request));
    }
}
