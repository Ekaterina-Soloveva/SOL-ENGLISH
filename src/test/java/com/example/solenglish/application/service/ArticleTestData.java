package com.example.solenglish.application.service;

import com.example.solenglish.application.dto.ArticleDTO;
import com.example.solenglish.application.model.Article;
import java.util.Arrays;
import java.util.List;


public interface ArticleTestData {
    ArticleDTO ARTICLE_DTO_1 = new ArticleDTO("title1",
            "key_words1",
            "author1",
            "introduction1",
            "body1",
            "conclusion1");

    ArticleDTO ARTICLE_DTO_2 = new ArticleDTO("title2",
            "key_words2",
            "author2",
            "introduction2",
            "body2",
            "conclusion2");


    ArticleDTO ARTICLE_DTO_3_DELETED = new ArticleDTO("title3",
            "key_words3",
            "author3",
            "introduction3",
            "body3",
            "conclusion3");


    List<ArticleDTO> ARTICLE_DTO_LIST = Arrays.asList(ARTICLE_DTO_1, ARTICLE_DTO_2, ARTICLE_DTO_3_DELETED);


    Article ARTICLE_1 = new Article("article1",
            "author1",
           "key_words1",
            "introduction1",
            "body1",
            "conclusion1");

    Article ARTICLE_2 = new Article("article2",
            "author2",
            "key_words2",
            "introduction2",
            "body2",
            "conclusion2");

    Article ARTICLE_3 = new Article("article3",
            "author3",
            "key_words3",
            "introduction3",
            "body3",
            "conclusion3");


    List<Article> ARTICLE_LIST = Arrays.asList(ARTICLE_1, ARTICLE_2, ARTICLE_3);
}

