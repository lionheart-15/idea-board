package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.dto.CommentRequestDto;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // db에 값 저장
    public void save(CommentRequestDto commentRequestDto){
        // controller에서 받아온 dto를 entity로 변환해서 comment에 저장
        Comment comment = commentRequestDto.toEntity();

        // repository에 comment를 save
        commentRepository.save(comment); // null pointer
    }

    public Board findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) { return null; }
        return optionalBoard.get();
    }

    // 특정 id의 db값 다 가져오기
    public Comment CommentView(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public Page<Comment> findByUserId(Long userId, Pageable pageable) {
        return commentRepository.findByUserId(userId,pageable);
    }
}
