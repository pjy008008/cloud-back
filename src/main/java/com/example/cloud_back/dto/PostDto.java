package com.example.cloud_back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

public class PostDto {

    @Getter
    @Setter
    @Schema(description = "게시글 생성 요청 DTO")
    public static class CreateRequest {
        @Schema(description = "게시글 제목", example = "JPA가 무엇인가요?")
        private String title;
        @Schema(description = "게시글 내용", example = "JPA에 대해 자세히 알려주세요.")
        private String content;
    }

    @Getter
    @Setter
    @Schema(description = "게시글 수정 요청 DTO")
    public static class UpdateRequest {
        @Schema(description = "수정할 게시글 제목", example = "JPA와 Hibernate의 관계")
        private String title;
        @Schema(description = "수정할 게시글 내용", example = "JPA는 명세이고 Hibernate는 구현체입니다.")
        private String content;
    }

    @Getter
    @Setter
    @Schema(description = "게시글 응답 DTO")
    public static class Response {
        private Long id;
        private String title;
        private String content;
        @Schema(description = "작성자 아이디")
        private String authorUsername;
        private LocalDateTime createdAt;

        public Response(Long id, String title, String content, String authorUsername, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.authorUsername = authorUsername;
            this.createdAt = createdAt;
        }
    }
}