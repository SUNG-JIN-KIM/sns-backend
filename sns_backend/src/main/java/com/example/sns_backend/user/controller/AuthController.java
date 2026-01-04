package com.example.sns_backend.user.controller;

import com.example.sns_backend.user.domain.User;
import com.example.sns_backend.user.dto.LoginRequest;
import com.example.sns_backend.user.dto.LoginResponse;
import com.example.sns_backend.user.dto.UserResponse;
import com.example.sns_backend.user.dto.UserSignUpRequest;
import com.example.sns_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")   // ✅ 최종 경로: /api/auth/...
public class AuthController {

    private final UserService userService;

    // ✅ 회원가입: POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @RequestBody @Valid UserSignUpRequest request
    ) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    // ✅ 로그인: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // ✅ 내 정보 조회: GET /api/auth/me
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @AuthenticationPrincipal User user
    ) {
        UserResponse response = userService.getMe(user);
        return ResponseEntity.ok(response);
    }
}
