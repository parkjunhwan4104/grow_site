package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.File;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor

public class ArticleSaveForm {


    @NotBlank(message="제목을 입력해 주세요")
    private String title;

    @NotBlank(message="내용을 입력해 주세요")
    private String body;

    private Long board_id;




    public ArticleSaveForm(Article articleDTO){
        this.title= articleDTO.getTitle();
        this.body= articleDTO.getBody();;
        this.board_id=articleDTO.getBoard().getId();
    }





}
