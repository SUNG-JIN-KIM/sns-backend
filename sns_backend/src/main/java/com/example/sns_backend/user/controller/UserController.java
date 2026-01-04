package com.example.sns_backend.user.controller;

import com.example.sns_backend.user.domain.User;
import com.example.sns_backend.user.dto.UpdateProfileRequest;
import com.example.sns_backend.user.dto.UserProfileResponse;
import com.example.sns_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // ✅ 내 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @AuthenticationPrincipal User user
    ) {
        UserProfileResponse response = userService.getMyProfile(user);
        return ResponseEntity.ok(response);
    }

    // ✅ 특정 유저 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId
    ) {
        UserProfileResponse response = userService.getProfile(currentUser, userId);
        return ResponseEntity.ok(response);
    }

    // ✅ 내 프로필 수정
    @PatchMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        UserProfileResponse response = userService.updateMyProfile(user, request);
        return ResponseEntity.ok(response);
    }
}
