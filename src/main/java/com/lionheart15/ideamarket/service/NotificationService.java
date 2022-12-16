package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.NotificationCreateRequest;
import com.lionheart15.ideamarket.domain.dto.NotificationResponse;
import com.lionheart15.ideamarket.domain.entity.Notification;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.NotificationRepository;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Notification save(NotificationCreateRequest request) {
        return notificationRepository.save(request.toEntity(
                userRepository.findById(request.getUserId()).get(),
                boardRepository.findById(request.getBoardId()).get()));
    }

    public List<NotificationResponse> findByUserId(Long userId, Pageable pageable) {
        Page<Notification> notificationList = notificationRepository.findByUserIdAndIsChecked(userId, false, pageable);
        List<NotificationResponse> responseList =  notificationList.stream()
                .map(notification -> {
                    return NotificationResponse.of(notification);
                }).collect(Collectors.toList());

        return responseList;
    }

    public void deleteById(Long notId) {
        notificationRepository.deleteById(notId);
    }
}
