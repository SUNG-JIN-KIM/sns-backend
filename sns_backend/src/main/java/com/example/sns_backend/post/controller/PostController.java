package com.example.sns_backend.post.controller;

import com.example.sns_backend.post.dto.PostCreateRequest;
import com.example.sns_backend.post.dto.PostResponse;
import com.example.sns_backend.post.service.PostService;
import com.example.sns_backend.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // ✅ 피드 조회: 나 + 팔로우한 사람들 글
    @GetMapping("/feed")
    public ResponseEntity<Page<PostResponse>> getFeed(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponse> responses = postService.getFeed(user, page, size);
        return ResponseEntity.ok(responses);
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostResponse response = postService.createPost(user, request);
        return ResponseEntity.ok(response);
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    // 전체 리스트 조회
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponse> responses = postService.getPosts(page, size);
        return ResponseEntity.ok(responses);
    }
}
