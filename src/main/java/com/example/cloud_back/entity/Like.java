package com.example.cloud_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "likes") // 'Like'는 예약어라 테이블 이름 지정
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
}
