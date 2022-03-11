package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ArticleListDTO {

    private Long id;
    private String title;
    private LocalDateTime regDate;

    private String memberNickName;




    public ArticleListDTO(Article article){
        this.id=article.getId();
        this.title=article.getTitle();
        this.regDate=article.getRegDate();
        this.memberNickName=article.getMember().getNickName();


    }


}
