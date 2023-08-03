package com.example.solenglish.application.controller.mvc;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@Transactional
@Rollback(value = true)
public class MVCProgressControllerTest extends CommonTestMVC {



    /**
     * Метод, тестирующий просмотр пользователем прогресса обучения  через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном просмотре
     *
     * @throws Exception - любая ошибка
     */

    @Test
    @DisplayName("Просмотр пользователем прогресса обучения  MVC контроллер")
    @Order(0)
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user2")
    protected void getProgress() throws Exception{
        log.info("Тест просмотра пользователем прогресса обучения через MVC начат!");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/progress/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/users/viewUserProgress"))
                .andReturn();
    }


    @Override
    protected void createObject() throws Exception {

    }

    @Override
    protected void updateObject() throws Exception {

    }

    @Override
    protected void deleteObject() throws Exception {

    }

    @Override
    protected void listAll() throws Exception {

    }
}
