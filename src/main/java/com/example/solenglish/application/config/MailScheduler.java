package com.example.solenglish.application.config;

import com.example.solenglish.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MailScheduler {

    private final UserService userService;
    private final JavaMailSender javaMailSender;

    public MailScheduler(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    //https://crontab.cronhub.io/
    //https://crontab.guru/#15_14_1_*_*

    //Крон на каждую минуту: "0 0/1 * 1/1 * *"
    //"0 0 6 * * ?"
    @Scheduled(cron = "0 0 9 * * ?") // каждый день в 9 утра
    public void sentMailsToDebtors() {
        log.info("Запуск планировщика по проверки должников....");
        //List<String> emails = userService.getUserEmailsWithDelayedRentDate();
//        if (emails.size() > 0) {
//            SimpleMailMessage simpleMailMessage = MailUtils.createMailMessage(
//                    emails,
//                    "Напоминание о просрочке книг(и)",
//                    "Вы злостный нарушитель!!! Верните книгу!!"
//            );
           // javaMailSender.send(simpleMailMessage);
       // }
    }
}

