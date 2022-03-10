package com.grow_site.grow_site.Dao;


import com.grow_site.grow_site.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {


}
