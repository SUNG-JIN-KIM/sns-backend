package com.example.sns_backend.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    @NotBlank
    private String content;

    // 대댓글이면 부모 댓글 ID, 일반 댓글이면 null
    private Long parentId;
}
