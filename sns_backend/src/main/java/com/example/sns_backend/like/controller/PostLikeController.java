package com.example.sns_backend.like.controller;

import com.example.sns_backend.like.dto.PostLikeResponse;
import com.example.sns_backend.like.service.PostLikeService;
import com.example.sns_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/likes")   // ✅ 이 부분 중요!
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping       // ✅ 추가 경로 없음 → POST /api/posts/{postId}/likes
    public ResponseEntity<PostLikeResponse> like(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = postLikeService.like(user, postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<PostLikeResponse> unlike(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = postLikeService.unlike(user, postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostLikeResponse> getStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = postLikeService.getStatus(user, postId);
        return ResponseEntity.ok(response);
    }
}
