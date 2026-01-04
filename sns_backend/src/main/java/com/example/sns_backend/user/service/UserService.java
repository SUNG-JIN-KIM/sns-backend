package com.example.sns_backend.user.service;

import com.example.sns_backend.follow.repository.FollowRepository;
import com.example.sns_backend.global.jwt.JwtTokenProvider;
import com.example.sns_backend.user.domain.User;
import com.example.sns_backend.user.dto.*;
import com.example.sns_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FollowRepository followRepository;

    // ✅ 회원가입: 컨트롤러에서 userService.signup(...) 호출
    @Transactional
    public UserResponse signup(UserSignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();

        User saved = userRepository.save(user);

        // ⚠️ UserResponse.from(...) 은 네 DTO에 있는 정적 메서드 이름에 맞춰 써줘
        return UserResponse.from(saved);
    }

    // ✅ 로그인: 컨트롤러에서 userService.login(...) 호출
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

        return new LoginResponse(token);
    }

    // ✅ 내 정보 조회 (간단 버전) : /api/auth/me 에서 사용
    public UserResponse getMe(User currentUser) {
        if (currentUser == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }
        return UserResponse.from(currentUser);
    }

    // ✅ 특정 유저 프로필 조회 (프로필 화면용)
    public UserProfileResponse getProfile(User currentUser, Long targetUserId) {
        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        long followerCount = followRepository.countByFollowing(target);
        long followingCount = followRepository.countByFollower(target);

        boolean me = currentUser != null && currentUser.getId().equals(target.getId());
        boolean following = false;

        if (!me && currentUser != null) {
            following = followRepository.existsByFollowerAndFollowing(currentUser, target);
        }

        return UserProfileResponse.of(
                target,
                followerCount,
                followingCount,
                me,
                following
        );
    }

    // ✅ 내 프로필 조회
    public UserProfileResponse getMyProfile(User currentUser) {
        return getProfile(currentUser, currentUser.getId());
    }

    // ✅ 내 프로필 수정
    @Transactional
    public UserProfileResponse updateMyProfile(User currentUser, UpdateProfileRequest request) {
        // 닉네임 중복 체크 (현재 내 닉네임은 예외)
        userRepository.findByNickname(request.getNickname())
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
                });

        currentUser.changeNickname(request.getNickname());
        currentUser.changeBio(request.getBio());
        currentUser.changeProfileImageUrl(request.getProfileImageUrl());

        long followerCount = followRepository.countByFollowing(currentUser);
        long followingCount = followRepository.countByFollower(currentUser);

        return UserProfileResponse.of(
                currentUser,
                followerCount,
                followingCount,
                true,
                false
        );
    }
}
