package com.grow_site.grow_site.service;


import com.grow_site.grow_site.Dao.BoardRepository;
import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public Board getBoard(Long id){

        Optional<Board> boardOptional = boardRepository.findById(id);
        boardOptional.orElseThrow(

                ()-> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        return boardOptional.get();


    }
}
