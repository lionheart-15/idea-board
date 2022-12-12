package com.lionheart15.ideamarket.domain.dto;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {
    private Long id;                // 댓글 id
    private String content;         // 댓글 내용
    private User user;              // 작성자
    private Board board;            // 댓글을 작성한 글 id

    // 댓글 생성 시간
//    private LocalDateTime createAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private LocalDateTime createAt;

    // dto -> entity
    // 매개변수가 Board로 되어있으니까.... board를 받아와서 comment로 반환하겠다.
    // comment를 만들면 comment repository로 저장한다.
//    public Comment toEntity(Board board) {
//        Comment comment = Comment.builder()
//                .id(id)
//                .content(content)
//                .user(user)
//                .board(board)
//                .build();
//        return comment;
//    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .createdAt(createAt)
                .user(user)
                .board(board)
                .build();
    }



}
