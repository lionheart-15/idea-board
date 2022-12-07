package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByCategory(String category, Pageable pageable);
    Page<Board> findByCategoryAndTitleContains(String category, String title, Pageable pageable);
    Page<Board> findByCategoryAndUserNameContains(String category, String username, Pageable pageable);

}
