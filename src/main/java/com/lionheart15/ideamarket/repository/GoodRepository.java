package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good,Long> {
}
