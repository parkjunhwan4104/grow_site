package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards/{name}")
    public String showBoardDetail(@PathVariable(name="name")String name, Model model){
        try {
            Board findBoard = boardService.getBoard(name);

            model.addAttribute("board", findBoard);
            return "user/board/detail";

        }
        catch (Exception e){

            return "redirect:/";
        }

    }


}
