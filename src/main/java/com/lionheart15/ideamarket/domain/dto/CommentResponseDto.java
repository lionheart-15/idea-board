package com.lionheart15.ideamarket.domain.dto;

import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private String content;
    private String userName;
    private LocalDateTime createdAt;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder().content(comment.getContent()).userName(comment.getUser().getName()).createdAt(comment.getCreatedAt()).build();
    }



//    // entity -> dto
//    public CommentResponseDto(Comment comment) {
//        this.id = comment.getId();
//        this.comment = getComment();
//        this.createAt = getCreateAt();
//        this.user = getUser();
//    }
}
