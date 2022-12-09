package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lionheart15.ideamarket.domain.entity.Notification;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
