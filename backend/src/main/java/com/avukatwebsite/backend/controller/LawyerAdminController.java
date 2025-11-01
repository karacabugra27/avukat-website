package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.AdminCreateLawyerRequest;
import com.avukatwebsite.backend.dto.request.AdminUpdateLawyerPasswordRequest;
import com.avukatwebsite.backend.dto.response.PasswordResetResponse;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.service.LawyerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lawyers")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class LawyerAdminController {

    private final LawyerService lawyerService;

    @PostMapping
    public ResponseEntity<ResponseLawyer> create(@Valid @RequestBody AdminCreateLawyerRequest request) {
        ResponseLawyer created = lawyerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{lawyerId}/password")
    public ResponseEntity<Void> updatePassword(@Positive @PathVariable Long lawyerId,
                                               @Valid @RequestBody AdminUpdateLawyerPasswordRequest request) {
        lawyerService.updatePassword(lawyerId, request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lawyerId}/reset-password")
    public ResponseEntity<PasswordResetResponse> resetPassword(@Positive @PathVariable Long lawyerId) {
        String temporaryPassword = lawyerService.resetPassword(lawyerId);
        return ResponseEntity.ok(new PasswordResetResponse(temporaryPassword));
    }

    @DeleteMapping("/{lawyerId}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long lawyerId) {
        lawyerService.delete(lawyerId);
        return ResponseEntity.noContent().build();
    }
}
