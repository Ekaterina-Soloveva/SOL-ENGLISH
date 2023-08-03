package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.ArticleDTO;
import com.example.solenglish.application.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCArticleControllerTest
        extends CommonTestMVC {

    @Autowired
    private ArticleService articleService;

    //Создаем новую статью для создания через контроллер (тест дата)
    private final ArticleDTO articleDTO = new ArticleDTO("MVC_TestArticleTitle",
            "TestAuthor",
            "TestKeyWords",
            "TestIntro",
            "TestBody",
            "TestConclusion");
    private final ArticleDTO articleDTOUpdated = new ArticleDTO("MVC_TestArticleTitle_UPDATED",
            "TestAuthor",
            "TestKeyWords",
            "TestIntro",
            "TestBody",
            "TestConclusion");

    /**
     * Метод, тестирующий просмотр всех статей через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном просмотре
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @DisplayName("Просмотр всех статей через MVC контроллер")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void listAll() throws Exception {
        log.info("Тест просмотра статей MVC начат!");
        MvcResult result = mvc.perform(get("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/articles/viewAllArticles"))
                .andExpect(model().attributeExists("articles"))
                .andReturn();
    }


    /**
     * Метод, тестирующий создание статьи через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном создании
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @Order(1)
    @DisplayName("Создание статьи через MVC контроллер")
    @WithMockUser(username = "teacher", roles = "TEACHER", password = "teacher")
    protected void createObject() throws Exception {
        log.info("Тест по созданию статьи через MVC начат");
        mvc.perform(MockMvcRequestBuilders.post("/articles/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .flashAttr("articleForm", articleDTO)
                                .accept(MediaType.APPLICATION_JSON)
                         .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"))
                .andExpect(redirectedUrlTemplate("/articles"))
                .andExpect(redirectedUrl("/articles"));
        log.info("Тест по созданию  статьи через MVC закончен!");
    }


    /**
     * Метод, тестирующий обновление  статьи через MVC-контроллер.
     * Авторизуемся под пользователем admin,
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном обновлении
     *
     * @throws Exception - любая ошибка
     */

    @Order(2)
    @Test
    @DisplayName("Обновление  статьи через MVC контроллер")
    @WithMockUser(username = "teacher", roles = "TEACHER", password = "teacher")
    //@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "andy_user")
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению  статьи через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title"));
        ArticleDTO foundArticleForUpdate = articleService.searchArticles(articleDTO.getKeyWords(), pageRequest).getContent().get(0);
        foundArticleForUpdate.setTitle(articleDTOUpdated.getKeyWords());
        mvc.perform(post("/articles/update/{id}", foundArticleForUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("articleForm", foundArticleForUpdate)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
        log.info("Тест по обновлению  статьи  через MVC закончен успешно");
    }


    /**
     * Метод, тестирующий удаление статьи через MVC-контроллер.
     * Авторизуемся под пользователем admin,
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном удалении
     *
     * @throws Exception - любая ошибка
     */
    @Order(3)
    @Test
    @DisplayName("Софт удаление статьи через MVC контроллер, тестирование 'articles/delete'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void deleteObject() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title"));
        ArticleDTO foundArticleForDelete = articleService.searchArticles(articleDTOUpdated.getKeyWords(), pageRequest).getContent().get(0);
        foundArticleForDelete.setDeleted(true);
        mvc.perform(get("/articles/delete/{id}", foundArticleForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));

        ArticleDTO deletedArticle = articleService.getOne(foundArticleForDelete.getId());
        assertTrue(deletedArticle.isDeleted());
        log.info("Тест по soft удалению статьи через MVC закончен успешно!");
    }


}
