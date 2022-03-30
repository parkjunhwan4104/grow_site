package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.File;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleModifyForm {

    @NotBlank(message="제목을 입력해 주세요")
    private String title;

    @NotBlank(message="내용을 입력해 주세요")
    private String body;



    public ArticleModifyForm(Article article){
        this.title=article.getTitle();
        this.body=article.getBody();


    }


}
