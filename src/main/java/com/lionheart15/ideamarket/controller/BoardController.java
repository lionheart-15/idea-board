package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardDto;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/boards")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

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
        boardService.write(board);
        return "redirect:/boards/view/" + board.getId();
    }

    // delete
    @GetMapping("/delete/{id}") // 이게.. 되네? 아직 삭제 버튼을 누르는 것은 구현하지 않았다.
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
}
