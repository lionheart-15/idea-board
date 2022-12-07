package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{category}")
    public String boardList(@PathVariable String category, Model model,
                            @RequestParam(required = false) String keyword, @RequestParam(required = false) String searchOption,
                            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> boardPage = null;
        if(searchOption == null) {
            boardPage = boardService.findByCategory(category, pageable);
        }
        else if(searchOption.equals("title")) {
            boardPage = boardService.findByCategoryAndTitle(category, keyword, pageable);
        } else if(searchOption.equals("userName")) {
            boardPage = boardService.findByCategoryAndUserName(category, keyword, pageable);
        }

        List<BoardListResponse> responseList = boardPage.stream()
                .map(board -> {
                    return BoardListResponse.of(board);
                }).collect(Collectors.toList());

        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchOption", searchOption);
        model.addAttribute("boards", responseList);
        model.addAttribute("previous", boardPage.previousOrFirstPageable().getPageNumber());
        model.addAttribute("next", boardPage.nextOrLastPageable().getPageNumber());
        model.addAttribute("searchCount", boardPage.getTotalElements());

        return "list";
    }

}

