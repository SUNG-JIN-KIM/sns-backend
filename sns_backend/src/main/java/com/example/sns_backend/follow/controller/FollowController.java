package com.example.sns_backend.follow.controller;

import com.example.sns_backend.follow.dto.FollowStatusResponse;
import com.example.sns_backend.follow.dto.FollowUserResponse;
import com.example.sns_backend.follow.service.FollowService;
import com.example.sns_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    // 팔로우 하기
    @PostMapping("/api/users/{targetUserId}/follow")
    public ResponseEntity<FollowStatusResponse> follow(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long targetUserId
    ) {
        FollowStatusResponse response = followService.follow(currentUser, targetUserId);
        return ResponseEntity.ok(response);
    }

    // 언팔로우
    @DeleteMapping("/api/users/{targetUserId}/follow")
    public ResponseEntity<FollowStatusResponse> unfollow(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long targetUserId
    ) {
        FollowStatusResponse response = followService.unfollow(currentUser, targetUserId);
        return ResponseEntity.ok(response);
    }

    // 팔로우 상태 조회 (프로필 화면에서 "팔로우 중인지"와 카운트)
    @GetMapping("/api/users/{targetUserId}/follow")
    public ResponseEntity<FollowStatusResponse> getStatus(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long targetUserId
    ) {
        FollowStatusResponse response = followService.getStatus(currentUser, targetUserId);
        return ResponseEntity.ok(response);
    }

    // 특정 유저의 팔로워 목록
    @GetMapping("/api/users/{userId}/followers")
    public ResponseEntity<List<FollowUserResponse>> getFollowers(@PathVariable Long userId) {
        List<FollowUserResponse> responses = followService.getFollowers(userId);
        return ResponseEntity.ok(responses);
    }

    // 특정 유저의 팔로잉 목록
    @GetMapping("/api/users/{userId}/followings")
    public ResponseEntity<List<FollowUserResponse>> getFollowings(@PathVariable Long userId) {
        List<FollowUserResponse> responses = followService.getFollowings(userId);
        return ResponseEntity.ok(responses);
    }
}
