package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestContactMessage;
import com.avukatwebsite.backend.dto.response.ResponseContactMessage;
import com.avukatwebsite.backend.service.ContactMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@Validated
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<ResponseContactMessage> create(@Valid @RequestBody RequestContactMessage request) {
        ResponseContactMessage created = contactMessageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseContactMessage>> getAll() {
        return ResponseEntity.ok(contactMessageService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        contactMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
