package com.example.solenglish.application.constants;

public interface MailConstants {

    String MAIL_MESSAGE_FOR_REMEMBER_PASSWORD = """
          Здравствуйте! Вы получили это письмо, так как с вашего аккаунта была отправлена заявка на восстановление пароля.\n
          Для восстановления пароля перейдите по ссылке: http://localhost:8083/users/change-password?uuid=""";

    String MAIL_SUBJECT_FOR_REMEMBER_PASSWORD = "Восстановление пароля на сайте SOL-ENGLISH";

    String MAIL_SUBJECT_FOR_CONTACT = "Письмо от пользователя сайта SOL-ENGLISH";
}
