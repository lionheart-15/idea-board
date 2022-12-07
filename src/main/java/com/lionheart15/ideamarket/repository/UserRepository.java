package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Notification;
import com.lionheart15.ideamarket.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
