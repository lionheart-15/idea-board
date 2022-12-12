package com.lionheart15.ideamarket.domain.dto;

import com.lionheart15.ideamarket.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {

    private Long id;
    private String title;
    private String userName;
    private int commentCount;
    private int goodCount;
    private String createdAt;
    private String category;

    public static BoardListResponse of(Board board) {
        return BoardListResponse.builder()
                .id(board.getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .userName(board.getUser().getName())
                .commentCount(board.getComments().size())
                .goodCount(board.getGoods().size())
                .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

    }
}
