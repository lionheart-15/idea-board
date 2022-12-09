package com.lionheart15.ideamarket.controller;

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

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final GoodService goodService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @PageableDefault(size=10) Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            Optional<User> optionalUser = userService.findByLoginId(auth.getName());
            log.info("authName: {}", auth.getName());
            if(optionalUser.isPresent()) {
                log.info("loginUserName : {}", optionalUser.get().getName());
                User loginUser = optionalUser.get();
                //model.addAttribute("userLoginId", loginUser.getLoginId());
                model.addAttribute("userId", loginUser.getId());
            }
        }
        Page<Board> boardPage = goodService.printPopularList(pageable);
        model.addAttribute("list", boardPage);
        return "main";
    }
}
