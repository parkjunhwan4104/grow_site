package com.grow_site.grow_site.service;

import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.Dao.ArticleRepository;
import com.grow_site.grow_site.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm) throws IllegalStateException{
        Article article= Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()

        );

        articleRepository.save(article);  //entity를 db안에 저장




    }
}
