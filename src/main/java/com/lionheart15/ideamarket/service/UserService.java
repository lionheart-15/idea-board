package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.UserSignUpDto;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
    public User save(UserSignUpDto dto) {
        return userRepository.save(dto.toEntity(encoder.encode(dto.getPassword())));
    }
      
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }
}
