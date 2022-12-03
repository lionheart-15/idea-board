package com.lionheart15.ideaboard.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;               // 제목
    private String text;                // 내용
    private LocalDateTime createdAt;    // 작성일
    private LocalDateTime updatedAt;    // 수정일
    private String imageFile;           // 이미지 파일 url
    private String category;            // 카테고리 이름

    // Content : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Content : Comment = 1 : N
    @OneToMany(mappedBy = "content")
    private List<Comment> comments = new ArrayList<>();

    // Content : Like = 1 : N
    @OneToMany(mappedBy = "content")
    private List<Like> likes = new ArrayList<>();

    // Content : Notification = 1 : N
    @OneToMany(mappedBy = "content")
    private List<Notification> notifications = new ArrayList<>();
}
