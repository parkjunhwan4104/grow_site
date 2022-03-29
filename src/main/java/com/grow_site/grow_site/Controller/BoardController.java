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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;

    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name="id")Long id, Model model, @RequestParam(name="page",defaultValue = "1") int page, @RequestParam(name="searchKeyword",defaultValue ="")String searchKeyword){

        int size=10;
        try {
            Board findBoard = boardService.getBoard(id);

            List<ArticleListDTO> articleListDTOList=articleService.getArticleListByBoardId(id);

            List<ArticleListDTO> store=new ArrayList<>();

            for(ArticleListDTO listDTO:articleListDTOList){
                if(listDTO.getTitle().contains(searchKeyword)){
                    store.add(listDTO);
                }
            }

            if(store.size()!=0){
                for(ArticleListDTO listDTO:store){
                    articleListDTOList=store;
                }
            }

            Collections.reverse(articleListDTOList);

            int startIndex=(page-1)*size;
            int lastIndex = ((page -1) * size) + 9;

            int lastPage=(int)Math.ceil(articleListDTOList.size()/(double)size);

            if(page==lastPage){
                lastIndex= articleListDTOList.size();

            }
            else if(page> lastPage){
                return "redirect:/";
            }
            else{
                lastIndex+=1;
            }

            List<ArticleListDTO> articlePage= articleListDTOList.subList(startIndex, lastIndex);

            if(!searchKeyword.equals("")&& store.size()==0){
                articlePage=store;
            }

            model.addAttribute("articleList",articlePage);
            model.addAttribute("board", findBoard);
            model.addAttribute("maxPage",lastPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("keyword",searchKeyword);
            return "user/board/detail";

        }
        catch (Exception e){

            return "redirect:/";
        }

    }


}
