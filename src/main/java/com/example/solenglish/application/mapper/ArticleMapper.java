package com.example.solenglish.application.mapper;

import com.example.solenglish.application.dto.ArticleDTO;
import com.example.solenglish.application.model.Article;
import com.example.solenglish.application.repository.ArticleRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ArticleMapper extends GenericMapper<Article, ArticleDTO> {

    private final ArticleRepository articleRepository;

    public ArticleMapper(ModelMapper modelMapper, ArticleRepository articleRepository) {
        super(Article.class, ArticleDTO.class, modelMapper);
        this.articleRepository = articleRepository;
    }

    @Override
    protected void mapSpecificFields(ArticleDTO source, Article destination) {

    }

    @Override
    protected void mapSpecificFields(Article source, ArticleDTO destination) {

    }

    @Override
    protected void setupMapper() {

    }

}





