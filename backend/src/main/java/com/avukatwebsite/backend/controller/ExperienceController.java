package com.avukatwebsite.backend.controller;


import com.avukatwebsite.backend.dto.request.RequestExperience;
import com.avukatwebsite.backend.dto.response.ResponseExperience;
import com.avukatwebsite.backend.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ResponseExperience> createExperience(@RequestBody RequestExperience request) {
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseExperience> update(@PathVariable Long id, @RequestBody RequestExperience request) {
        return ResponseEntity.ok(experienceService.update(id, request));
    }
}
