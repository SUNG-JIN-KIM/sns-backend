package com.example.sns_backend.comment.service;

import com.example.sns_backend.comment.domain.Comment;
import com.example.sns_backend.comment.dto.CommentCreateRequest;
import com.example.sns_backend.comment.dto.CommentResponse;
import com.example.sns_backend.comment.repository.CommentRepository;
import com.example.sns_backend.post.domain.Post;
import com.example.sns_backend.post.repository.PostRepository;
import com.example.sns_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public CommentResponse createComment(User user, Long postId, CommentCreateRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment.CommentBuilder builder = Comment.builder()
                .post(post)
                .user(user)
                .content(request.getContent());

        Comment comment = builder.build();

        // 부모 댓글이 있는 경우 (대댓글)
        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));

            // 부모 댓글이 같은 게시글에 속해 있는지 체크
            if (!parent.getPost().getId().equals(postId)) {
                throw new IllegalArgumentException("부모 댓글은 동일한 게시글에 속해야 합니다.");
            }

            comment.setParent(parent);
        }

        Comment saved = commentRepository.save(comment);

        return CommentResponse.from(saved);
    }

    // 게시글별 댓글 목록 조회
    public List<CommentResponse> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    // 댓글 삭제 (작성자만)
    @Transactional
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
