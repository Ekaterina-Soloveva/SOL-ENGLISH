package com.example.solenglish.application.constants;

import java.util.List;


public interface SecurityConstants {


    List<String> RESOURCES_WHITE_LIST = List.of(
            "/resources/**",
            "/static/**",
            "/js/**",
            "/css/**",
            "/images/**",
            "/audio/**",
            "/",
            "/index",
            "/about",
            "/infoAboutCompany",
            "/error",
            "/articles/**",
            "/articles/viewAllArticles",
            "/swagger-ui/**",
            "/webjars/bootstrap/5.3.0/**",
            "/webjars/bootstrap/5.3.0/css/**",
            "/webjars/bootstrap/5.3.0/js/**",
            "/v3/api-docs/**"
    );

    List<String> TOPICS_WHITE_LIST = List.of(
            "/topics",
            "/topics/add",
            "/topics/update",
            "/topics/delete"
    );

    List<String> ARTICLES_WHITE_LIST = List.of(
            "/articles",
            "/articles/add",
            "/articles/{id}",
            "/articles/delete/{id}",
            "/articles/restore/{id}",
            "/articles/update/{id}",
            "/articles/search"
    );
    List<String> TESTS_PERMISIONS_LIST = List.of(
            "/tests/",
            "/tests/entranceTest"
    );

    List<String> USER_WHITE_LIST = List.of(
            "/login",
            "/users/registration",
            "/users/remember-password",
            "/users/change-password",
            "/users/changeProfile",
            "/users/viewUserCurriculum",
            "/curriculum",
            "/tests/entranceTest"

    );

}

