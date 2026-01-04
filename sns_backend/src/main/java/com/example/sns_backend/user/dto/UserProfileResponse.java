package com.example.sns_backend.user.dto;

import com.example.sns_backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String bio;

    private long followerCount;
    private long followingCount;

    private boolean me;          // 내가 나 자신인지
    private boolean following;   // 내가 이 유저를 팔로우 중인지

    public static UserProfileResponse of(
            User user,
            long followerCount,
            long followingCount,
            boolean me,
            boolean following
    ) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .bio(user.getBio())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .me(me)
                .following(following)
                .build();
    }
}
