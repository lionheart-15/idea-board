package com.lionheart15.ideamarket;

import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.domain.entity.dto.UserSignUpDto;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //회원가입 로직
    @PostMapping ("/create")
    public String create(UserSignUpDto dto){
        log.info(dto.toString());

        User user = dto.toEntity();
        log.info(user.toString());

        User savedUser = userRepository.save(user);
        log.info(savedUser.toString());
        return "main";
    }

}
