package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.File;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ArticleModifyForm {

    private String title;

    private String body;



    public ArticleModifyForm(Article article){
        this.title=article.getTitle();
        this.body=article.getBody();


    }


}
