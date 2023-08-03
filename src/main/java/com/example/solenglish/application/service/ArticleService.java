package com.example.solenglish.application.service;

import com.example.solenglish.application.dto.ArticleDTO;

import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.Article;
import com.example.solenglish.application.repository.ArticleRepository;
import com.example.solenglish.application.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ArticleService extends GenericService<Article, ArticleDTO> {

    protected ArticleService(GenericRepository<Article> repository,
                             GenericMapper<Article, ArticleDTO> mapper) {
        super(repository, mapper);
    }

    /**
     *
     * @param id
     * мягкое удаление статьи
     */
    public void deleteSoft(final Long id) {
        Article articleToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("Объект не найден"));
        articleToDelete.setDeleted(true);
        repository.save(articleToDelete);

    }

    /**
     *
     * @param keyWord
     * @param pageable
     * @return Страницы со статьями, найденными по ключевым словам
     */
    public Page<ArticleDTO> searchArticles(final String keyWord,
                                           Pageable pageable) {
        Page<Article> articles = ((ArticleRepository) repository).findAllByKeyWordsContainsIgnoreCaseAndIsDeletedFalse(keyWord, pageable);
        List<ArticleDTO> result = mapper.toDTOs(articles.getContent());
        return new PageImpl<>(result, pageable, articles.getTotalElements());
    }



}
