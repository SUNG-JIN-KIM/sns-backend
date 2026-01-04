package com.example.sns_backend.post.service;

import com.example.sns_backend.post.domain.Post;
import com.example.sns_backend.post.dto.PostCreateRequest;
import com.example.sns_backend.post.dto.PostResponse;
import com.example.sns_backend.post.repository.PostRepository;
import com.example.sns_backend.user.domain.User;

// ⬇⬇⬇ 추가
import com.example.sns_backend.follow.repository.FollowRepository;
import com.example.sns_backend.follow.domain.Follow;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    // 게시글 작성
    @Transactional
    public PostResponse createPost(User user, PostCreateRequest request) {

        Post post = Post.builder()
                .user(user)
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .build();

        Post saved = postRepository.save(post);

        return PostResponse.from(saved);
    }

    // 단건 조회
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        return PostResponse.from(post);
    }

    // 목록 조회 (페이징)
    public Page<PostResponse> getPosts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponse::from);
    }

        // ✅ 피드 조회: 나 + 내가 팔로우한 사람들의 글
        public Page<PostResponse> getFeed(User currentUser, int page, int size) {
            PageRequest pageable = PageRequest.of(page, size);

            // 1) 내가 팔로우한 사람들의 ID 모으기
            List<Follow> followings = followRepository.findByFollowerId(currentUser.getId());

            List<Long> userIds = new ArrayList<>();
            userIds.add(currentUser.getId()); // 내 글도 같이 보기

            userIds.addAll(
                    followings.stream()
                            .map(f -> f.getFollowing().getId())
                            .collect(Collectors.toList())
            );

            // 팔로우한 사람이 아무도 없고 내 글도 없으면
            if (userIds.isEmpty()) {
                return Page.empty(pageable);
            }

            return postRepository
                    .findByUserIdInOrderByCreatedAtDesc(userIds, pageable)
                    .map(PostResponse::from);
        }
    }
