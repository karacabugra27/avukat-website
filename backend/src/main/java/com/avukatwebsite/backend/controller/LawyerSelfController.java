package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.ChangeOwnPasswordRequest;
import com.avukatwebsite.backend.dto.request.UpdateOwnProfileRequest;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.service.LawyerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawyers/me")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('LAWYER','SUPER_ADMIN')")
public class LawyerSelfController {

    private final LawyerService lawyerService;

    @PutMapping("/profile")
    public ResponseEntity<ResponseLawyer> updateProfile(@AuthenticationPrincipal UserDetails principal,
                                                        @Valid @RequestBody UpdateOwnProfileRequest request) {
        ResponseLawyer response = lawyerService.updateOwnProfile(principal.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails principal,
                                               @Valid @RequestBody ChangeOwnPasswordRequest request) {
        lawyerService.changeOwnPassword(principal.getUsername(), request.currentPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }
}
