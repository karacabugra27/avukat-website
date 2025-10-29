package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.RequestMembership;
import com.avukatwebsite.backend.dto.response.ResponseMembership;
import com.avukatwebsite.backend.service.MembershipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
@Validated
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<ResponseMembership> create(@Valid @RequestBody RequestMembership request) {
        ResponseMembership created = membershipService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseMembership>> getAll() {
        return ResponseEntity.ok(membershipService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMembership> getById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(membershipService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        membershipService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMembership> update(@Positive @PathVariable Long id, @Valid @RequestBody RequestMembership request) {
        return ResponseEntity.ok(membershipService.update(id, request));
    }
}
