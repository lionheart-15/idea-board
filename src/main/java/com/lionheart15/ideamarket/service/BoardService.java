package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> findByCategory(String category, Pageable pageable) {
        return boardRepository.findByCategory(category, pageable);
    }

    public Page<Board> findByCategoryAndTitle(String category, String title, Pageable pageable) {
        return boardRepository.findByCategoryAndTitleContains(category, title, pageable);
    }

    public Page<Board> findByCategoryAndUserName(String category, String userName, Pageable pageable) {
        return boardRepository.findByCategoryAndUserNameContains(category, userName, pageable);
    }
}
