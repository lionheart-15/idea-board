package com.lionheart15.ideamarket.service;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Good;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.BoardRepository;
import com.lionheart15.ideamarket.repository.GoodRepository;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public void like(User user, Board board){
        Good good = Good.builder()
                .user(user)
                .board(board)
                .build();
        goodRepository.save(good);
    }

    public void dbClear(){
        goodRepository.deleteAll();
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }
    public void setInit(){
        int size = 5;
           User user = User.builder()
                    .birth("1")
                    .email("2")
                    .gender(3)
                    .loginId("4")
                    .name("5")
                    .password("6")
                    .phoneNumber("7")
                    .role("8")
                    .build();
            userRepository.save(user);


        LocalDateTime time = LocalDateTime.now();
        Board board = Board.builder()
                    .title("name" + user.getId())
                    .content("mycontentis")
                    .createdAt(time)
                    .updatedAt(null)
                    .imageFile("url")
                    .category("일반게시판")
                    .user(user)
                    .build();
            boardRepository.save(board);
            for(int i=0;i<5;i++){
                like(user,board);
            }
    }
    // 접근방법
    // 전체 값들 중에서 board_id 가 많은 순서대로 정렬
    // 많은 순서대로 10개까지 출력
    // 만약 좋아요 개수가 같다면?
    // ? Board에 List<Good> 데이터에 접근하는 방법? -> list는 good 개수를 반환할 수 있는가

    // 1. 맵을 이용한방법
    // 2. priorityqueue?

    //로직 설명
    //map에서 조회한 boardId 중복 시 cnt ++
    //value 값 '내림차순'정렬 한 후 list로 저장
    //해당 리스트를 10개까지 조회 후 데이터 리스트 저장
    //저장된 리스트 반환
    public List<Board> findBy(){
        HashMap<Long,Integer> map = new HashMap<>();
        List<Good> list = goodRepository.findAll();
        for(int i=0;i<list.size();i++){
            Board board = list.get(i).getBoard();
            Long boardId = board.getId();
            if(!map.containsKey(boardId)){
                map.put(boardId,1);
            }else {
                map.put(boardId, map.get(boardId) + 1);
            }
        }
        List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort(new Comparator<Map.Entry<Long, Integer>>() {
            @Override
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        for(Map.Entry<Long,Integer> entry : entryList){
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        }


        List<Board> boardList = new ArrayList<>();

        for(int i = 0 ;i<10;i++){
            Optional<Board> optionalBoard = boardRepository.findById(entryList.get(i).getKey());
            if(optionalBoard.isEmpty()){
                continue;
            }else{
                boardList.add(optionalBoard.get());
            }
        }
        return boardList;
    }


}

