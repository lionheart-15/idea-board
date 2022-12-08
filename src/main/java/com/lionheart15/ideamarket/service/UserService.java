package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.domain.entity.dto.UserSignUpDto;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String loginId) {
        boolean usernameDuplicate = userRepository.existsByLoginId(loginId);
        return usernameDuplicate;
    }
    public User save(String birth,String email,int gender,String loginId,String name,String password,String phoneNumber) {
        User user = User.builder()
                .birth(birth)
                .email(email)
                .gender(gender)
                .loginId(loginId)
                .name(name)
                .password(encoder.encode(password))
                .phoneNumber(phoneNumber)
                .role("user")
                .build();

        userRepository.save(user);
        return user;
    }

}
