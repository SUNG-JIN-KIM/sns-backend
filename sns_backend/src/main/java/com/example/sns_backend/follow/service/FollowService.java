package com.example.sns_backend.follow.service;

import com.example.sns_backend.follow.domain.Follow;
import com.example.sns_backend.follow.dto.FollowStatusResponse;
import com.example.sns_backend.follow.dto.FollowUserResponse;
import com.example.sns_backend.follow.repository.FollowRepository;
import com.example.sns_backend.user.domain.User;
import com.example.sns_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 하기
    @Transactional
    public FollowStatusResponse follow(User currentUser, Long targetUserId) {

        if (currentUser.getId().equals(targetUserId)) {
            throw new IllegalArgumentException("본인을 팔로우할 수 없습니다.");
        }

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다."));

        if (followRepository.existsByFollowerIdAndFollowingId(currentUser.getId(), targetUserId)) {
            throw new IllegalArgumentException("이미 팔로우 중인 사용자입니다.");
        }

        Follow follow = Follow.builder()
                .follower(currentUser)
                .following(targetUser)
                .build();

        followRepository.save(follow);

        return buildStatus(currentUser, targetUser);
    }

    // 언팔로우
    @Transactional
    public FollowStatusResponse unfollow(User currentUser, Long targetUserId) {

        if (currentUser.getId().equals(targetUserId)) {
            throw new IllegalArgumentException("본인을 언팔로우 할 수 없습니다.");
        }

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFollowerIdAndFollowingId(currentUser.getId(), targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 중이 아닌 사용자입니다."));

        followRepository.delete(follow);

        return buildStatus(currentUser, targetUser);
    }

    // 팔로우 상태 조회 (프로필 화면에서 사용)
    public FollowStatusResponse getStatus(User currentUser, Long targetUserId) {

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다."));

        return buildStatus(currentUser, targetUser);
    }

    // 프로필 유저의 팔로워 목록
    public List<FollowUserResponse> getFollowers(Long userId) {
        List<Follow> followers = followRepository.findByFollowingId(userId);

        return followers.stream()
                .map(Follow::getFollower)
                .map(FollowUserResponse::from)
                .collect(Collectors.toList());
    }

    // 프로필 유저가 팔로우하는 사람 목록
    public List<FollowUserResponse> getFollowings(Long userId) {
        List<Follow> followings = followRepository.findByFollowerId(userId);

        return followings.stream()
                .map(Follow::getFollowing)
                .map(FollowUserResponse::from)
                .collect(Collectors.toList());
    }

    // ===== 내부 메서드 =====

    private FollowStatusResponse buildStatus(User currentUser, User targetUser) {
        boolean following =
                !currentUser.getId().equals(targetUser.getId()) &&
                        followRepository.existsByFollowerIdAndFollowingId(currentUser.getId(), targetUser.getId());

        long followerCount = followRepository.countByFollowingId(targetUser.getId());
        long followingCount = followRepository.countByFollowerId(targetUser.getId());

        return new FollowStatusResponse(following, followerCount, followingCount);
    }
}
