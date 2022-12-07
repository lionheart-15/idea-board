package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
// repo
public interface BoardRepository extends JpaRepository<Board, Long> {
}
