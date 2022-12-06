package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    //db에 저장하고 꺼내오기 위한 repo
}
