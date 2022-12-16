package com.lionheart15.ideamarket.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isChecked;          // 알림 확인 여부
    private boolean typeChecked;        // 알림이 채팅인지 여부 (true: 채팅, false: 댓글)
    private LocalDateTime createdAt;    // 알림 발생 일시
    private String notUrl;              // 알림 클릭 시 이동할 주소

    // Notification : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Notification : Board = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}
