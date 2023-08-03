package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.RoleDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static com.example.solenglish.application.constants.UserRolesConstants.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = true)
public class MVCUserControllerTest
        extends CommonTestMVC {

    @Autowired
    private UserService userService;


    private final UserDTO userDTO = new UserDTO("MVC_TestUserLogin",
            "testPassword",
            "test_email@mail.ru",
            LocalDate.now(),
            "testFirstName",
            "testLastName",
            "testSecondName",
            "89130019251",
            null,
            null,
            new ArrayList<>(),
            new ArrayList<>());


    /**
     * Метод, тестирующий просмотр всех пользователей через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном просмотре
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @DisplayName("Просмотр всех пользователей через MVC контроллер")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void listAll() throws Exception {
        log.info("Тест просмотра пользователей MVC начат!");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/users/viewAllUsers"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
    }





    /**
     * Метод, тестирующий создание нового пользователя через MVC-контроллер.
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
    @DisplayName("Создание пользователя через MVC контроллер")
    @WithAnonymousUser
    protected void createObject() throws Exception {
        log.info("Тест по созданию пользователя через MVC начат");
        mvc.perform(MockMvcRequestBuilders.post("/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .flashAttr("userForm", userDTO)
                                .accept(MediaType.APPLICATION_JSON)
                         .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(redirectedUrl("http://localhost/login"));
        log.info("Тест по созданию пользователя через MVC закончен!");
    }


    @Order(2)
    @Test
    @DisplayName("Обновление профиля через MVC контроллер")
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user2")
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению профиля через MVC начат успешно");

        UserDTO foundUserForUpdate = userService.getUserByLogin("user2");
        foundUserForUpdate.setFirstName(userDTO.getFirstName());
        mvc.perform(post("/users/changeProfile")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("changeProfileForm", foundUserForUpdate)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                       .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile/ + customUserDetails.getUserId()"))
                .andExpect(redirectedUrl("/users/profile/ + customUserDetails.getUserId()"));
        log.info("Тест по обновлению профиля через MVC закончен успешно");
    }



    @Override
    protected void deleteObject() throws Exception {
    }


    /**
     * Метод, тестирующий смену роли пользователя на учителя через MVC-контроллер.
     * Авторизуемся под пользователем admin,
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном просмотре
     *
     * @throws Exception - любая ошибка
     */

    @Test
    @DisplayName("Смена роли пользователя на учителя через MVC контроллер")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void addTeacher() throws Exception {
        log.info("Тест смены роли пользователя на учителя MVC начат!");

        mvc.perform(get("/users/addTeacher/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(redirectedUrl("/users"));
    }


}
