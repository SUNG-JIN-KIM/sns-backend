# 📸 Mini SNS Backend

사진을 올리고, 댓글과 좋아요를 남길 수 있는 미니 SNS 백엔드 서버입니다.  
팀 프로젝트가 아니라 **기획부터 설계, 개발, 배포까지 혼자 진행하는 개인 프로젝트**를 목표로 합니다.

---

## 🚀 주요 기능

- 회원가입 / 로그인 (JWT 인증)
- 유저 프로필 조회
- 게시글 작성 / 수정 / 삭제
- 게시글 목록 조회 (페이징)
- 댓글 작성 / 삭제 (대댓글 지원)
- 게시글 좋아요 / 좋아요 취소
- 유저 팔로우 / 언팔로우 (팔로우한 유저 피드 조회 예정)

---

## 🛠 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Build Tool**: Gradle
- **DB**: MySQL (또는 PostgreSQL)
- **ORM**: Spring Data JPA
- **Auth**: Spring Security + JWT
- **Infra**: AWS EC2 (예정)
- **Etc**: Lombok, Validation, Swagger/OpenAPI

---

## 📁 프로젝트 구조 (예정)

```text
src
 └─ main
    ├─ java
    │   └─ com.example.sns
    │       ├─ user
    │       ├─ post
    │       ├─ comment
    │       ├─ follow
    │       ├─ global
    │       │    ├─ config
    │       │    └─ entity
    │       └─ ...
    └─ resources
        ├─ application.yml
        └─ ...
