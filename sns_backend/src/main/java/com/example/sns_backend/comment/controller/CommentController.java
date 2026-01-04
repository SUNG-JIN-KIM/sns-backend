package com.example.sns_backend.comment.controller;

import com.example.sns_backend.comment.dto.CommentCreateRequest;
import com.example.sns_backend.comment.dto.CommentResponse;
import com.example.sns_backend.comment.service.CommentService;
import com.example.sns_backend.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 (혹은 대댓글)
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.createComment(user, postId, request);
        return ResponseEntity.ok(response);
    }

    // 게시글의 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId
    ) {
        List<CommentResponse> responses = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responses);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        // postId는 URL 일관성용, 로직에선 commentId로만 처리하지만
        // 필요하면 postId 검증 추가 가능
        commentService.deleteComment(user, commentId);
        return ResponseEntity.noContent().build();
    }
}
