package com.lionheart15.ideaboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/develop")
    public String develop() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "hello";
    }

    @PostMapping("/hello2")
    public String hello2(){
        return "happy";
    }

    //test commit
}
