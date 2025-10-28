package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestAppointment;
import com.avukatwebsite.backend.dto.response.ResponseAppointment;
import com.avukatwebsite.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ResponseAppointment> createAppointment(@RequestBody RequestAppointment request) {
        ResponseAppointment created = appointmentService.createAppointment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseAppointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<List<ResponseAppointment>> getByScheduleId(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getByScheduleId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAppointment> update(@PathVariable Long id, @RequestBody RequestAppointment request) {
        return ResponseEntity.ok(appointmentService.update(id, request));
    }

}
