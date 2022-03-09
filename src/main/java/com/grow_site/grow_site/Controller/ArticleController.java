package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/articles/add")
    public String showAddArticle(Model model){

        model.addAttribute("ArticleSaveForm",new ArticleSaveForm());

        return "user/article/add";
    }

    @PostMapping("/articles/add")
    public String doAdd(){

        return "";
    }




}
