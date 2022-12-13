package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.dto.CommentRequestDto;
import com.lionheart15.ideamarket.domain.dto.CommentResponseDto;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.service.BoardService;
import com.lionheart15.ideamarket.service.CommentService;
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
import java.time.format.DateTimeFormatter;
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
    private final CommentService commentService;

    @GetMapping("/{category}")
    public String boardList(@PathVariable String category, Model model,
                            @RequestParam(required = false) String keyword, @RequestParam(required = false) String searchOption,
                            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("userId", userId);

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
        if(searchOption != null && searchOption.equals("userName")) {
            model.addAttribute("searchUser", true);
        }
        model.addAttribute("boards", responseList);
        model.addAttribute("lastPage", boardPage.getTotalPages());

        log.info("{}", boardPage.getNumber());

        model.addAttribute("nowPage", boardPage.getNumber() + 1);
        if(boardPage.hasPrevious()) {
            model.addAttribute("previousPage", boardPage.getNumber());
        }
        if(boardPage.hasNext()) {
            model.addAttribute("nextPage", boardPage.getNumber() + 2);
        }

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
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("userId", userId);
        if(userId != null) {
            model.addAttribute("userLoginId", userService.findById(userId).getLoginId());
            if(userId == boardService.boardView(id).getUser().getId()) {
                model.addAttribute("myBoard", true);
            }
        }

        List<Comment> commentList = commentService.findByBoardId(id);

        // list<Comment>를 response dto로 변환하는 과정(data type)
        List<CommentResponseDto> responseList = commentList.stream()
                .map(list -> {
                    return CommentResponseDto.of(list);
                }).collect(Collectors.toList());

        model.addAttribute("comment", responseList);

        model.addAttribute("board", boardService.boardView(id));
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> loginUser = userService.findByLoginId(loginId);
        if(loginUser.isPresent() && goodService.isPresent(id,loginUser.get().getId())){
            model.addAttribute("good",1);
        }
        return "detail";
    }

    // write
    @GetMapping("/{category}/write")
    public String createBoard(@PathVariable String category, Model model) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userService.findByLoginId(loginId).get();
        model.addAttribute("userId", loginUser.getId());
        
       String role = loginUser.getRole();

        if(category.equals("Notice")) {
            if (role.equals("ADMIN")) {
                return "writer";
            } else {
                return "";
            }
        }
        return "writer";
    }

    @PostMapping("/{category}/write")
    public String boardWrite(@PathVariable String category, Board board) {
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        board.setCategory(category);
        board.setUser(userService.findById(userId));
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
        Long userId = userService.getLoginUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("userId", userId);


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
        Optional<User> loginUser = userService.findByLoginId(loginId);
        if(loginUser.isPresent()){
            goodService.createGood(boardId,loginUser.get().getId());
        }else{
            log.info("로그인 유저 아이디가 없습니다!");
        }
        return "redirect:/boards/view/{boardId}";
    }

    // 댓글
    @PostMapping("/comment/{boardId}")
    public String createComment(@PathVariable Long boardId, CommentRequestDto commentRequestDto) {

        // dto setting
        Board board = boardService.boardView(boardId);
        commentRequestDto.setBoard(board);
        commentRequestDto.setCreateAt(LocalDateTime.now());

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> loginUser = userService.findByLoginId(loginId);
        if(loginUser.isPresent()){
            commentRequestDto.setUser(loginUser.get());
        }else{
            log.info("로그인 유저 아이디가 없습니다!");
        }

        log.info("board : " + commentRequestDto.getBoard().getId());
        log.info("content : " + commentRequestDto.getContent());
        log.info("setCreateAt : " + commentRequestDto.getCreateAt());
        commentService.save(commentRequestDto);

        return "redirect:/boards/view/{boardId}";
    }


}
