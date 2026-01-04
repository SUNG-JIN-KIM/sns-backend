package com.example.sns_backend.post.dto;

import com.example.sns_backend.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private String imageUrl;

    private Long authorId;
    private String authorNickname;

    private LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .authorId(post.getUser().getId())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
