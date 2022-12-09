package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }
}
