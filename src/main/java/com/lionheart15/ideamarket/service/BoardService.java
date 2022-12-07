package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.BoardDto;
import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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
    
    // db에 값 넣기
    public void write(Board board) {
        boardRepository.save(board);
    }

    // delete
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    // 특정 게시글 view
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }
}
