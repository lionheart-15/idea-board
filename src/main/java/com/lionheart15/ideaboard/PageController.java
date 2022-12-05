package com.lionheart15.ideaboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    @GetMapping(value = {"/hello", "", "/"})
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
    @GetMapping("/list")
    public String listPage() {
        return "list";
    }

    @GetMapping("/writer")
    public String writerPage() {
        return "writer";
    }

    @GetMapping("/detail")
    public String detailPage() {
        return "detail";
    }

    @GetMapping("/mypage")
    public String myPage(Model model) {
        model.addAttribute("loginInfo", 1);
        return "mypage";
    }

    @GetMapping("/mypage2")
    public String myPage2() {
        return "mypage";
    }
}
