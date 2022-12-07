package com.lionheart15.ideamarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class MypageTestController {
    @GetMapping("/loginMypage")
    public String loginMypage() {
        return "mypage01";
    }
    @GetMapping("/notLoginMypage")
    public String notLoginmyPage() {
        return "mypage02";
    }

}
