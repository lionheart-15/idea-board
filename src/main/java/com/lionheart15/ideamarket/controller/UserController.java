package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.UserSignUpDto;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        if(userId != null) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            log.info("auth name : {}", auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @PostMapping("/create")
    public  String create(@ModelAttribute UserSignUpDto dto, Model model){

        if(userService.checkUsernameDuplication(dto.getLoginId())){
            model.addAttribute("iddup", true);
            return "signup";
        }

        userService.save(dto);
        return"login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        if(userId != null) {
            return "redirect:/";
        }

        return "signup";
    }

    @GetMapping("/{userId}/myPage")
    public String myPage(@PathVariable Long userId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByLoginId(auth.getName());

        // 다른 유저의 마이페이지에 접근하려는 경우 => 홈으로 보냄
        if(optionalUser.isEmpty() || optionalUser.get().getId() != userId)  {
            model.addAttribute("wrongId", true);
        }
        model.addAttribute("userId", userId);
        model.addAttribute("userLoginId", optionalUser.get().getLoginId());

        return "mypage01";
    }
}
