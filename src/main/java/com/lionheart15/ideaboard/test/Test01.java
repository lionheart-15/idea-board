package com.lionheart15.ideaboard.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test01 {
    @GetMapping("/test")
    public String mypageTest() {
        return "mypage";
    }
}
