package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public Page<Comment> findByUserId(Long userId, Pageable pageable) {
        return commentRepository.findByUserId(userId,pageable);
    }
}
