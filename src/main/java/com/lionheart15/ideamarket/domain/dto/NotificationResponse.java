package com.lionheart15.ideamarket.domain.dto;

import com.lionheart15.ideamarket.domain.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class NotificationResponse {

    private Long id;
    private Boolean isChecked;          // 알림 확인 여부
    private boolean typeChecked;        // 알림이 채팅인지 여부 (true: 채팅, false: 댓글)
    private LocalDateTime createdAt;    // 알림 발생 일시
    private String notUrl;              // 알림 클릭 시 이동할 주소
    private Long boardId;

    public static NotificationResponse of(Notification notification) {
        return new NotificationResponse(notification.getId(), notification.getIsChecked(), notification.isTypeChecked(),
                notification.getCreatedAt(), notification.getNotUrl(), notification.getBoard().getId());
    }
}
