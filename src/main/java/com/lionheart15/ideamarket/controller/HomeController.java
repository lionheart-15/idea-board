package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.dto.NotificationResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.service.GoodService;
import com.lionheart15.ideamarket.service.NotificationService;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final GoodService goodService;
    private final NotificationService notificationService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @PageableDefault(size=10) @Qualifier("board") Pageable boardPageable,
                       @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) @Qualifier("notification")Pageable notificationPageable) {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("userId", userId);
        if(userId != null) {
            List<NotificationResponse> notifications = notificationService.findByUserId(userId, notificationPageable);
            model.addAttribute("notifications", notifications);
            if(notifications.size() != 0) {
                model.addAttribute("notification", true);
            }
        }

        Page<Board> boardPage = goodService.printPopularList(boardPageable);
        List<BoardListResponse> responseList = boardPage.stream()
                .map(board -> {
                    return BoardListResponse.of(board);
                }).collect(Collectors.toList());

        model.addAttribute("boards", responseList);
        return "main";
    }
}
