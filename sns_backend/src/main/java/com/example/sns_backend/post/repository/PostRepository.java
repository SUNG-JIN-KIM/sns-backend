package com.example.sns_backend.post.repository;

import com.example.sns_backend.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 최신 글 순으로 페이징 조회
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // ✅ 피드용: 특정 유저들 목록의 글만 조회
    Page<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds, Pageable pageable);
}
