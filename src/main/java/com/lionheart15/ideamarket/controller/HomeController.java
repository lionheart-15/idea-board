package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.service.GoodService;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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

    @GetMapping(value = {"", "/"})
    public String home(Model model, @PageableDefault(size=10) Pageable pageable) {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("userId", userId);

        Page<Board> boardPage = goodService.printPopularList(pageable);
        List<BoardListResponse> responseList = boardPage.stream()
                .map(board -> {
                    return BoardListResponse.of(board);
                }).collect(Collectors.toList());

        model.addAttribute("boards", responseList);
        return "main";
    }
}
