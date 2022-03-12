package com.grow_site.grow_site.DTO.article;

import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String body;
    private LocalDateTime regDate;

    private String memberNickName;
    private String memberLoginId;

    private Long boardId;
    private String boardName;

    public ArticleDTO(Article article){
        this.id=article.getId();
        this.title= article.getTitle();
        this.body=article.getBody();
        this.regDate=article.getRegDate();
        this.memberNickName=article.getMember().getNickName();
        this.memberLoginId=article.getMember().getLoginId();
        this.boardId=article.getBoard().getId();
        this.boardName=article.getBoard().getName();

    }

}
