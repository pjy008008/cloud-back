package com.example.cloud_back.service;

import com.example.cloud_back.dto.PostDto;
import com.example.cloud_back.entity.Post;
import com.example.cloud_back.entity.User;
import com.example.cloud_back.repository.PostRepository;
import com.example.cloud_back.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostDto.Response createPost(PostDto.CreateRequest request, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return new PostDto.Response(savedPost.getId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getAuthor().getUsername(), savedPost.getCreatedAt());
    }

    public List<PostDto.Response> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostDto.Response(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getUsername(), post.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public PostDto.Response getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return new PostDto.Response(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getUsername(), post.getCreatedAt());
    }

    @Transactional
    public PostDto.Response updatePost(Long id, PostDto.UpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        Post updatedPost = postRepository.save(post);
        return new PostDto.Response(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getAuthor().getUsername(), updatedPost.getCreatedAt());
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}