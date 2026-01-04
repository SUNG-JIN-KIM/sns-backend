package com.example.sns_backend.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponse {

    // 현재 이 사용자가 좋아요를 누른 상태인지
    private boolean liked;

    // 해당 게시글의 총 좋아요 수
    private long likeCount;
}
