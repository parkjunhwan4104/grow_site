package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.File;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleSaveForm {



    private String title;
    private String body;
    private Long board_id;



    /*
    public ArticleSaveForm(Article articleDTO){
        this.title= articleDTO.getTitle();
        this.body= articleDTO.getBody();;
        this.board_id=articleDTO.getBoardId();
    }

     */



}
