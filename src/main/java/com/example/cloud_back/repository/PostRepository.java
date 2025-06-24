package com.example.cloud_back.repository;

import com.example.cloud_back.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
