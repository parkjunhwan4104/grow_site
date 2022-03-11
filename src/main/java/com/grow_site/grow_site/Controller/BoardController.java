package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.article.ArticleListDTO;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.service.ArticleService;
import com.grow_site.grow_site.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;

    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name="id")Long id, Model model){
        try {
            Board findBoard = boardService.getBoard(id);
          List<ArticleListDTO> articleListDTOList=articleService.getArticleListByBoardId(id);


           model.addAttribute("articleList",articleListDTOList);
            model.addAttribute("board", findBoard);
            return "user/board/detail";

        }
        catch (Exception e){

            return "redirect:/";
        }

    }


}
