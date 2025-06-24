package com.example.cloud_back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    @Schema(description = "사용자 회원가입 요청 DTO")
    public static class RegistrationRequest {
        @Schema(description = "사용자 아이디", example = "testuser")
        private String username;
        @Schema(description = "비밀번호", example = "password123")
        private String password;
    }

    @Getter
    @Setter
    @Schema(description = "사용자 로그인 요청 DTO")
    public static class LoginRequest {
        @Schema(description = "사용자 아이디", example = "testuser")
        private String username;
        @Schema(description = "비밀번호", example = "password123")
        private String password;
    }

    @Getter
    @Setter
    @Schema(description = "로그인 응답 DTO")
    public static class LoginResponse {
        @Schema(description = "JWT 토큰")
        private String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}