package com.grow_site.grow_site.service;

import com.grow_site.grow_site.DTO.article.ArticleListDTO;
import com.grow_site.grow_site.Dao.ArticleRepository;
import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleListDTO> getArticleList(){
        List<Article> articles=articleRepository.findAll();

        List<ArticleListDTO> articleListDTOS=new ArrayList<>();

        for(Article article :articles){
            ArticleListDTO articleListDTO=new ArticleListDTO(article);

            articleListDTOS.add(articleListDTO);

        }
        return articleListDTOS;

    }

    @Transactional
    public void deleteArticle(Long id){
        Article article=articleRepository.getById(id);

        articleRepository.delete(article);

    }






}
