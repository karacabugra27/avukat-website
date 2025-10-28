package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestLawyerSchedule;
import com.avukatwebsite.backend.dto.response.ResponseLawyerSchedule;
import com.avukatwebsite.backend.service.LawyerScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class LawyerScheduleController {

    private final LawyerScheduleService lawyerScheduleService;

    @PostMapping
    public ResponseEntity<ResponseLawyerSchedule> create(RequestLawyerSchedule request) {
        ResponseLawyerSchedule created = lawyerScheduleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseLawyerSchedule>> getAll() {
        return ResponseEntity.ok(lawyerScheduleService.getAll());
    }

    @GetMapping("/lawyer/{id}")
    public ResponseEntity<List<ResponseLawyerSchedule>> getByLawyerId(@PathVariable Long id) {
        return ResponseEntity.ok(lawyerScheduleService.getByLawyerId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lawyerScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseLawyerSchedule> update(@PathVariable Long id, @RequestBody RequestLawyerSchedule request) {
        return ResponseEntity.ok(lawyerScheduleService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLawyerSchedule> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lawyerScheduleService.getById(id));
    }


}
