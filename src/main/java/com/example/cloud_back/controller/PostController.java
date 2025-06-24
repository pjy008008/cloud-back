package com.example.cloud_back.controller;

import com.example.cloud_back.dto.PostDto;
import com.example.cloud_back.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post API", description = "블로그 게시글 CRUD API")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다. JWT 토큰이 필요합니다.")
    @PostMapping
    public ResponseEntity<PostDto.Response> createPost(@RequestBody PostDto.CreateRequest request,
                                                       @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.createPost(request, userDetails.getUsername()));
    }

    @Operation(summary = "모든 게시글 조회", description = "모든 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PostDto.Response>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "특정 게시글 조회", description = "ID로 특정 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> getPostById(@Parameter(description = "게시글 ID") @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "게시글 수정", description = "ID로 특정 게시글을 수정합니다. 해당 게시글의 작성자만 수정할 수 있습니다.")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto.Response> updatePost(@Parameter(description = "게시글 ID") @PathVariable Long id,
                                                       @RequestBody PostDto.UpdateRequest request) {
        // 실제로는 수정 권한 검사 로직이 서비스 계층에 필요합니다.
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @Operation(summary = "게시글 삭제", description = "ID로 특정 게시글을 삭제합니다. 해당 게시글의 작성자만 삭제할 수 있습니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Parameter(description = "게시글 ID") @PathVariable Long id) {
        // 실제로는 삭제 권한 검사 로직이 서비스 계층에 필요합니다.
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}