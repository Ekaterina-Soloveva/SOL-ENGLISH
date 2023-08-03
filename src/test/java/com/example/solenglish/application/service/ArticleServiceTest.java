package com.example.solenglish.application.service;


import com.example.solenglish.application.dto.ArticleDTO;
import com.example.solenglish.application.mapper.ArticleMapper;
import com.example.solenglish.application.model.Article;
import com.example.solenglish.application.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.junit.jupiter.api.Order;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArticleServiceTest
        extends GenericTest<Article, ArticleDTO> {


    public ArticleServiceTest() {
        super();
        repository = Mockito.mock(ArticleRepository.class);
        mapper = Mockito.mock(ArticleMapper.class);
        service = new ArticleService((ArticleRepository) repository, (ArticleMapper) mapper);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(ArticleTestData.ARTICLE_LIST);
        Mockito.when(mapper.toDTOs(ArticleTestData.ARTICLE_LIST)).thenReturn(ArticleTestData.ARTICLE_DTO_LIST);

        List<ArticleDTO> articlesDTOS = service.listAll();
        log.info("Testing getAll(): {}", articlesDTOS);
        assertEquals(ArticleTestData.ARTICLE_LIST.size(), articlesDTOS.size());
    }


    @Order(2)
    @Test
    @Override
    public void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(ArticleTestData.ARTICLE_1));
        Mockito.when(mapper.toDTO(ArticleTestData.ARTICLE_1)).thenReturn(ArticleTestData.ARTICLE_DTO_1);

        ArticleDTO articleDTO = service.getOne(1L);
        log.info("Testing getOne(): {}", articleDTO);
        assertEquals(ArticleTestData.ARTICLE_DTO_1, articleDTO);
    }

    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(ArticleTestData.ARTICLE_DTO_1)).thenReturn(ArticleTestData.ARTICLE_1);
        Mockito.when(mapper.toDTO(ArticleTestData.ARTICLE_1)).thenReturn(ArticleTestData.ARTICLE_DTO_1);
        Mockito.when(repository.save(ArticleTestData.ARTICLE_1)).thenReturn(ArticleTestData.ARTICLE_1);

        ArticleDTO articleDTO = service.create(ArticleTestData.ARTICLE_DTO_1);
        log.info("Testing create(): {}", articleDTO);
    }

    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(ArticleTestData.ARTICLE_DTO_1)).thenReturn(ArticleTestData.ARTICLE_1);
        Mockito.when(mapper.toDTO(ArticleTestData.ARTICLE_1)).thenReturn(ArticleTestData.ARTICLE_DTO_1);
        Mockito.when(repository.save(ArticleTestData.ARTICLE_1)).thenReturn(ArticleTestData.ARTICLE_1);

        ArticleDTO articleDTO = service.update(ArticleTestData.ARTICLE_DTO_1);
        log.info("Testing create(): {}", articleDTO);
    }


}


