package com.lionheart15.ideaboard.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;        // 이름 (본명)
    private String birth;       // 생년월일 (YYYYMMDD)
    private int gender;         // 성별 (1: 남자, 2: 여자)
    private String loginId;     // 유저가 사용할 아이디
    private String password;    // 비밀번호
    private String phoneNumber; // 전화번호
    private String email;       // 이메일
    private String role;        // 권한 (user, admin, ...)

    // User : Board = 1 : N
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    // User : Comment = 1 : N
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    // User : Good = 1 : N
    @OneToMany(mappedBy = "user")
    private List<Good> goods = new ArrayList<>();

    // User : Notification = 1 : N
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

}
