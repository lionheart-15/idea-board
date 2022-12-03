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
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;               // 제목
    private String content;             // 내용
    private LocalDateTime createdAt;    // 작성일
    private LocalDateTime updatedAt;    // 수정일
    private String imageFile;           // 이미지 파일 url
    private String category;            // 카테고리 이름

    // Board : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Board : Comment = 1 : N
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    // Board : Good = 1 : N
    @OneToMany(mappedBy = "board")
    private List<Good> goods = new ArrayList<>();

    // Board : Notification = 1 : N
    @OneToMany(mappedBy = "board")
    private List<Notification> notifications = new ArrayList<>();
}
