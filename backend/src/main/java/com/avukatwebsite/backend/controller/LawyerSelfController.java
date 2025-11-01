package com.avukatwebsite.backend.controller;

import com.avukatwebsite.backend.dto.request.ChangeOwnPasswordRequest;
import com.avukatwebsite.backend.dto.request.UpdateOwnProfileRequest;
import com.avukatwebsite.backend.dto.response.ResponseLawyer;
import com.avukatwebsite.backend.service.LawyerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseLawyer> uploadPhoto(@AuthenticationPrincipal UserDetails principal,
                                                      @RequestPart("file") MultipartFile file) {
        ResponseLawyer response = lawyerService.updateProfileImage(principal.getUsername(), file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/photo")
    public ResponseEntity<Void> deletePhoto(@AuthenticationPrincipal UserDetails principal) {
        lawyerService.deleteProfileImage(principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}
