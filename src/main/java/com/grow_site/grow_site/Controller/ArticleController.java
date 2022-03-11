package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.article.ArticleDTO;
import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.ArticleService;
import com.grow_site.grow_site.service.BoardService;
import com.grow_site.grow_site.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberService memberService;


    @GetMapping("/boards/{id}/articles/add")
    public String showAddArticle(@PathVariable(name="id")Long id, Model model){

        Board board=boardService.getBoard(id);
        String boardName=board.getName();
        Long boardId=board.getId();

        model.addAttribute("boardName",boardName);
        model.addAttribute("boardId",boardId);
        model.addAttribute("ArticleSaveForm",new ArticleSaveForm());

        return "user/article/add";
    }

    @PostMapping("/boards/{id}/articles/add")
    public String doAddArticle(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, @PathVariable(name="id")Long id, Model model, Principal principal){
        Board board=boardService.getBoard(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("boardName",board.getName());
            model.addAttribute("boardId",board.getId());

            return "user/article/add";
        }

        try{



            Member member=memberService.getMember(principal.getName());
            articleService.save(articleSaveForm,member,board);

        }
        catch(IllegalStateException e){
            model.addAttribute("error_msg",e.getMessage());
            return "user/article/add";
        }
        return "redirect:/";
    }




}
