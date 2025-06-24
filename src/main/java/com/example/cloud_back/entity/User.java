package com.example.cloud_back.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password; // 실제 서비스에서는 BCrypt로 암호화

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}
