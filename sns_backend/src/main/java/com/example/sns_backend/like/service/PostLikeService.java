package com.example.sns_backend.like.service;

import com.example.sns_backend.like.domain.PostLike;
import com.example.sns_backend.like.dto.PostLikeResponse;
import com.example.sns_backend.like.repository.PostLikeRepository;
import com.example.sns_backend.post.domain.Post;
import com.example.sns_backend.post.repository.PostRepository;
import com.example.sns_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // 좋아요 누르기
    @Transactional
    public PostLikeResponse like(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 이미 좋아요 했다면 예외 (혹은 그냥 무시해도 됨)
        if (postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            throw new IllegalArgumentException("이미 좋아요한 게시글입니다.");
        }

        PostLike like = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(like);

        long count = postLikeRepository.countByPostId(postId);
        return new PostLikeResponse(true, count);
    }

    // 좋아요 취소
    @Transactional
    public PostLikeResponse unlike(User user, Long postId) {

        PostLike like = postLikeRepository.findByPostIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("아직 좋아요를 누르지 않은 게시글입니다."));

        postLikeRepository.delete(like);

        long count = postLikeRepository.countByPostId(postId);
        return new PostLikeResponse(false, count);
    }

    // 현재 상태 조회
    public PostLikeResponse getStatus(User user, Long postId) {
        boolean liked = postLikeRepository.existsByPostIdAndUserId(postId, user.getId());
        long count = postLikeRepository.countByPostId(postId);
        return new PostLikeResponse(liked, count);
    }
}
