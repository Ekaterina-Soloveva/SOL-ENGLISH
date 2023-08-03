package com.example.solenglish.application.constants;


public interface Errors {

    class Tests {
        public static final String ENTRANCE_TEST_ERROR = "Входной тест уже пройден, а учебный план составлен";
    }


    class Users {
        public static final String USER_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о пользователе";

        public static final String USER_NOT_FOUND_ERROR = "Такой пользователь не зарегистрирован на сайте";
    }


}
