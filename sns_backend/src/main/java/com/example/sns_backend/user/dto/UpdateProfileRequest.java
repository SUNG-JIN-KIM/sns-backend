package com.example.sns_backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfileRequest {

    @NotBlank
    private String nickname;

    private String bio;

    private String profileImageUrl;  // 일단 URL 문자열로 처리 (파일 업로드는 나중에)
}
