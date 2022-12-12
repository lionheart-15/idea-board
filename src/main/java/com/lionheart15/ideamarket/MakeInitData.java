package com.lionheart15.ideamarket;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.Good;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.CommentRepository;
import com.lionheart15.ideamarket.repository.GoodRepository;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MakeInitData {

    // 초기 데이터 만들어주는 코드
    // 프로그램 시작 시 실행됨 => ddl-auto: create로 안하면 계속 생성됨
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final GoodRepository goodRepository;
    private final BCryptPasswordEncoder encoder;
    private final CommentRepository commentRepository;

    @PostConstruct
    public void make(){

        // 로그인 테스트에 사용할 user, admin
        User user = User.builder()
                .name("user111").birth("20001111").gender(1).loginId("user").password(encoder.encode("1234"))
                .phoneNumber("01012345678").email("aaa@naver.com").role("USER")
                .build();

        User admin = User.builder()
                .name("admin111").birth("20001112").gender(2).loginId("admin").password(encoder.encode("1234"))
                .phoneNumber("01011111111").email("bbb@naver.com").role("ADMIN")
                .build();

        userRepository.save(user);
        userRepository.save(admin);

        User user1 = User.builder().gender(1).name("name1").loginId("user1").build();
        User user2 = User.builder().gender(2).name("name2").loginId("user2").build();
        User user3 = User.builder().gender(3).name("name3").loginId("user3").build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        LocalDateTime time = LocalDateTime.now();
        Board board1 = Board.builder().title("title1").content("mycontentis").createdAt(time).category("일반게시판").user(user).build();
        Board board2 = Board.builder().title("title2").content("mycontentis").createdAt(time).category("일반게시판").user(user).build();
        Board board3 = Board.builder().title("title3").content("mycontentis").createdAt(time).category("일반게시판").user(user).build();
        Board board4 = Board.builder().title("title4").content("mycontentis").createdAt(time).category("일반게시판").user(user1).build();
        Board board5 = Board.builder().title("title5").content("mycontentis").createdAt(time).category("일반게시판").user(user3).build();
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
        boardRepository.save(board5);

        Good good1 = Good.builder().user(user).board(board1).build();
        Good good4 = Good.builder().user(user).board(board5).build();
        Good good5 = Good.builder().user(user).board(board2).build();
        Good good7 = Good.builder().user(user).board(board3).build();
        Good good8 = Good.builder().user(user).board(board4).build();
        goodRepository.save(good1);
        goodRepository.save(good4);
        goodRepository.save(good5);
        goodRepository.save(good7);
        goodRepository.save(good8);

        Comment comment1 = Comment.builder().user(user).board(board1).content("content").createdAt(time).build();
        Comment comment2 = Comment.builder().user(user).board(board1).content("content").createdAt(time).build();
        Comment comment3 = Comment.builder().user(user).board(board1).content("content").createdAt(time).build();
        Comment comment4 = Comment.builder().user(user).board(board1).content("content").createdAt(time).build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);


    }
}
