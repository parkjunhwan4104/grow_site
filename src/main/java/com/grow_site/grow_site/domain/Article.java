package com.grow_site.grow_site.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Article {

    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="MEDIUMTEXT")
    private String body;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<File> files=new ArrayList<>();

    private LocalDateTime regDate=LocalDateTime.now();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    public static Article createArticle(String title,String body){
        Article article=new Article();
        article.title=title;
        article.body=body;


        return article;

    }

    public void setMember(Member member){
        this.member=member;
        member.getArticles().add(this);
    }

    public void setBoard(Board board){
        this.board=board;
        board.getArticles().add(this);

    }

    public void modifyArticle(String title,String body){

        this.title=title;
        this.body=body;

    }




}
