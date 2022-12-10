package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Good;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.GoodRepository;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodService {
    private final BoardRepository boardRepository;
    private final GoodRepository goodRepository;
    private final UserRepository userRepository;

    public Page<Board> printPopularList(Pageable pageable){
        Page<Board> boardList = boardRepository.popularList(pageable);
        return boardList;
    }

    public void createGood(Long boardId, Long userId){
        Optional<Good> good = goodRepository.findByBoardIdAndUserId(boardId, userId);
        Optional<Board> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if(good.isEmpty()){
            Good saveGood = Good.builder()
                            .user(user.get())
                            .board(board.get())
                            .build();
            goodRepository.save(saveGood);
        }else{
            goodRepository.deleteById(good.get().getId());
        }
    }

}

