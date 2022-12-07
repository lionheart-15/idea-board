package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.BoardDto;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

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
        return boardRepository.findById(id).get(); // id를 통해서 모든 칼럼을 가져온다.
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

}
