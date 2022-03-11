package com.grow_site.grow_site.service;

import com.grow_site.grow_site.DTO.article.ArticleDTO;
import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.Dao.ArticleRepository;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;



    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member, Board board) throws IllegalStateException{
        Article article= Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()

        );
        article.setMember(member);
        article.setBoard(board);

        articleRepository.save(article);  //entity를 db안에 저장




    }
    /*
    public Optional<Article> findById(Long id){
            return articleRepository.findById(id);

    }

    public Article getById(Long id) throws NoSuchElementException{

        Optional<Article> articleOption=findById(id);

        articleOption.orElseThrow(
                ()->new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        return articleOption.get();


    }

    public ArticleDTO getArticleById(Long id){
        Article article=getById(id);

        ArticleDTO articleDTO=new ArticleDTO(article);

        return articleDTO;

    }


    */

}
