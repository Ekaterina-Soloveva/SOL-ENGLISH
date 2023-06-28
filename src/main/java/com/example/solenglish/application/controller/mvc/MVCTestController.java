package com.example.solenglish.application.controller.mvc;


import com.example.solenglish.application.dto.TestDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.TestService;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;


@Controller
@Slf4j
@RequestMapping("/tests")
public class MVCTestController {

    private final TestService testService;
    private final UserService userService;


    public MVCTestController(UserService userService, TestService testService) {
        this.testService = testService;
        this.userService = userService;
    }

    @GetMapping("/entranceTest")
    public String getEntranceTest(Model model) {
        TestDTO entranceTest = testService.getOne(1L);
        model.addAttribute("entranceTest", entranceTest);
        return "/tests/entranceTest";
    }

    @PostMapping("/entranceTest")
    public String getEntranceTest(@ModelAttribute("entranceTest") TestDTO test) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));

        System.out.println(user.getTopicsDone()); //TODO:NLP???


        if (!Objects.isNull(user.getTopicsDone())) {
         testService.makeUserCurriculum(user, test.getNumberOfCorrectTasks());
            return "redirect:/curriculum/ + customUserDetails.getUserId()";
        } else {
            return "redirect:/curriculum/ + customUserDetails.getUserId()";

        }

    }


}
