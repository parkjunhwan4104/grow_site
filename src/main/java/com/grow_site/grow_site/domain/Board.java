package com.grow_site.grow_site.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board {


    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime regDate=LocalDateTime.now();

    @OneToOne(fetch=FetchType.LAZY)  //게시판을 생성한 사람과 게시판은 일대일 대응관계
    @JoinColumn(name="member_id")
    private Member member;   //보드에서 회원을 참조함 회원이 보드를 참조하지 않음

    public static Board createBoard(String name,Member member){
        Board board=new Board();
        board.name=name;
        board.member=member;

        return board;
    }
}
