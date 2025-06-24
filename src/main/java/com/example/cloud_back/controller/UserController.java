package com.example.cloud_back.controller;

import com.example.cloud_back.config.JwtTokenProvider;
import com.example.cloud_back.dto.UserDto;
import com.example.cloud_back.entity.User;
import com.example.cloud_back.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "사용자 가입 및 인증 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 아이디")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto.RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "로그인", description = "사용자 아이디와 비밀번호로 로그인하고 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 발급",
                    content = @Content(schema = @Schema(implementation = UserDto.LoginResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 비밀번호")
    })
    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@RequestBody UserDto.LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found")); // 예외 처리를 구체화하는 것이 좋습니다.
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 실제 서비스에서는 400 Bad Request나 401 Unauthorized를 반환하는 것이 더 적절합니다.
            throw new RuntimeException("Invalid password");
        }
        String token = jwtTokenProvider.createToken(user.getUsername());
        return ResponseEntity.ok(new UserDto.LoginResponse(token));
    }
}