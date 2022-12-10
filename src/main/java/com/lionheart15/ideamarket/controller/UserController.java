package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.entity.dto.UserSignUpDto;
import com.lionheart15.ideamarket.repository.UserRepository;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
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
    public  String create(@ModelAttribute UserSignUpDto dto ){

        if(userRepository.existsByLoginId(dto.getLoginId())){

            return "iddup";
        }

        userService.save(dto.getBirth(), dto.getEmail(), dto.getGender(),dto.getLoginId(),dto.getName(),dto.getPassword(),dto.getPhoneNumber());

        return"login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/{userId}/myPage")
    public String myPage() {
        return "mypage01";
    }
}
