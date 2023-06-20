package com.example.solenglish.application.repository;

import com.example.solenglish.application.model.Article;
import com.example.solenglish.application.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository
        extends GenericRepository<Article> {
    Page<Article> findAllByKeyWordsContainsIgnoreCaseAndIsDeletedFalse(String keyWord,
                                                                            Pageable pageable);
}
