package com.example.sns_backend.follow.dto;

import com.example.sns_backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowUserResponse {

    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String bio;

    public static FollowUserResponse from(User user) {
        return FollowUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .bio(user.getBio())
                .build();
    }
}
