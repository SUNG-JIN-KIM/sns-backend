package com.example.sns_backend.post.domain;

import com.example.sns_backend.global.entity.BaseTimeEntity;
import com.example.sns_backend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 이미지 URL (선택)
    @Column(length = 500)
    private String imageUrl;

    // == 연관관계/비즈니스 메서드 필요하면 여기 추가 ==
}
