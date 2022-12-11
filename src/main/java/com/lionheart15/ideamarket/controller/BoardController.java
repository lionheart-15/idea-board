package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.service.BoardService;
import com.lionheart15.ideamarket.service.GoodService;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final GoodService goodService;

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

    // list
    @GetMapping("/list")
    public String boardList() {
        return "list";
    }

    // view
    @GetMapping("/view/{id}")
    public String boardView(Model model, @PathVariable Long id) {
        model.addAttribute("board", boardService.boardView(id));
        return "detail";
    }

    // write
    @GetMapping("/write")
    public String createBoard() {
        return "writer";
    }

    @PostMapping("/create_post")
    public String boardWrite(Board board) {
        board.setCategory("일반게시판");
        board.setUser(userService.findById(1L));
        board.setCreatedAt(LocalDateTime.now());
        boardService.write(board);
        return "redirect:/boards/view/" + board.getId();
    }

    // delete
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.boardDelete(id);
        return "redirect:/boards/list";
    }

    // edit
    @GetMapping("/edit/{id}")
    public String boardEdit(@PathVariable Long id, Model model) {
        Optional<Board> optionalBoard = Optional.ofNullable(boardService.boardView(id));
        model.addAttribute("board", optionalBoard.get());
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable Long id, Board board) {
        // 기존의 글
        Board boardTemp = boardService.boardView(id);
        // 덮어 쓰기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.save(boardTemp);

        return "redirect:/boards/view/{id}";
    }

    @PostMapping("/like/{boardId}")
    public String likeBoard(@PathVariable Long boardId) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userService.findByLoginId(loginId).get();
        log.info("boardId : {}", boardId);
        log.info("loginUserId : {}", loginUser.getId());
        goodService.createGood(boardId, loginUser.getId());
        return "redirect:/boards/view/{boardId}";
    }
}
