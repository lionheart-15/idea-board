package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
