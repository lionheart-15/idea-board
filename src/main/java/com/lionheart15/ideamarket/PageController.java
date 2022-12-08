package com.lionheart15.ideamarket;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.UserRepository;
import com.lionheart15.ideamarket.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
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

    private final GoodService goodService;
    @GetMapping("/popular")
    public String popularPrint(Model model, @PageableDefault(size=10) Pageable pageable){
        Page<Board> boardPage = goodService.printPopularList(pageable);
        model.addAttribute("list", boardPage);
        return "main";
    }

    private final UserRepository userRepository;
    @GetMapping("/user")
    @ResponseBody
    public String userAuthTest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 로그인 한 유저를 SecurityContextHolder로 불러옴
        User user = userRepository.findByLoginId(auth.getName())
                .orElseThrow(() -> {
                    return new RuntimeException();
                });

        return "로그인 유저 이름 : " + user.getName() + "\n" + "로그인 유저 권한 : " + user.getRole();
    }

    @GetMapping("/admin")
    @ResponseBody
    public String adminAuthTest() {
        // 로그인 한 유저가 admin이여야만 들어갈 수 있는 페이지
        return "관리자만 들어올 수 있는 페이지입니다.";
    }
}
