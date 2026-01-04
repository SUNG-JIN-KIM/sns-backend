package com.example.sns_backend.comment.repository;

import com.example.sns_backend.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 달린 댓글들 (작성 순)
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
}
