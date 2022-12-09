package com.lionheart15.ideamarket;

import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.domain.entity.dto.UserSignUpDto;
import com.lionheart15.ideamarket.repository.UserRepository;
import com.lionheart15.ideamarket.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    //회원가입 로직
//    @PostMapping ("/create")
//    public String create(UserSignUpDto dto){
//        log.info(dto.toString());
//
//        User user = dto.toEntity();
//        user.setRole("user");
//        log.info(user.toString());
//
//        User savedUser = userRepository.save(user);
//        log.info(savedUser.toString());
//        return "main";
//    }
    @PostMapping("/create")
    public  String create(@ModelAttribute UserSignUpDto dto ){

       if(userRepository.existsByLoginId(dto.getLoginId())){

           return "iddup";
       }

        userService.save(dto.getBirth(), dto.getEmail(), dto.getGender(),dto.getLoginId(),dto.getName(),dto.getPassword(),dto.getPhoneNumber());

        return"login";
    }


}
