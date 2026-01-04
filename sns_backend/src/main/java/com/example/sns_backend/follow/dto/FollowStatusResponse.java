package com.example.sns_backend.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowStatusResponse {

    // 현재 로그인한 사용자가 이 유저를 팔로우 중인지
    private boolean following;

    // 이 유저(프로필 대상)의 팔로워 수
    private long followerCount;

    // 이 유저(프로필 대상)가 팔로우하는 사람 수
    private long followingCount;
}
