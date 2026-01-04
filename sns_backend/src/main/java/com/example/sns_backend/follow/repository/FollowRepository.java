package com.example.sns_backend.follow.repository;

import com.example.sns_backend.follow.domain.Follow;
import com.example.sns_backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowerId(Long followerId);     // 내가 몇 명을 팔로우하는지
    long countByFollowingId(Long followingId);   // 나를 몇 명이 팔로우하는지

    List<Follow> findByFollowerId(Long followerId);   // 내가 팔로우하는 사람들
    List<Follow> findByFollowingId(Long followingId); // 나를 팔로우하는 사람들

    // ✅ 팔로워 수 (나를 팔로우하는 사람)
    long countByFollowing(User following);

    // ✅ 팔로잉 수 (내가 팔로우하는 사람)
    long countByFollower(User follower);
}
