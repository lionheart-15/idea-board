package com.lionheart15.ideamarket.domain.dto;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Notification;
import com.lionheart15.ideamarket.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotificationCreateRequest {

    private Boolean isChecked;          // 알림 확인 여부
    private boolean typeChecked;        // 알림이 채팅인지 여부 (true: 채팅, false: 댓글)
    private LocalDateTime createdAt;    // 알림 발생 일시
    private String notUrl;              // 알림 클릭 시 이동할 주소
    private Long userId;
    private Long boardId;

    public Notification toEntity(User user, Board board) {
        return Notification.builder()
                .isChecked(isChecked)
                .typeChecked(typeChecked)
                .createdAt(createdAt)
                .notUrl(notUrl)
                .user(user)
                .board(board)
                .build();
    }
}
