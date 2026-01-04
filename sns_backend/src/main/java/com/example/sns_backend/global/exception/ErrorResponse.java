package com.example.sns_backend.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    // HTTP 상태 코드 (예: 400, 401, 404...)
    private final int status;

    // 간단한 에러 코드 (원하면 나중에 "USER_NOT_FOUND" 같은 코드로 확장)
    private final String code;

    // 프론트에 보여 줄 메시지
    private final String message;
}
