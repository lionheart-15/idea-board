package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Good;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.GoodRepository;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Page<Board> printPopularList(Pageable pageable){
        Page<Board> boardList = boardRepository.popularList(pageable);
        return boardList;
    }

}

