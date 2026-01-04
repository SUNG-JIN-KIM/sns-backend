package com.example.sns_backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank
    private String content;

    // 선택 값 – 없어도 됨
    private String imageUrl;
}
