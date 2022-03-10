package com.grow_site.grow_site.DTO.article;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleSaveForm {

    private String title;
    private String body;
    private Long board_id;

    public ArticleSaveForm(ArticleDTO articleDTO){
        this.title= articleDTO.getTitle();
        this.body= articleDTO.getBody();;
        this.board_id=articleDTO.getBoardId();
    }
}
