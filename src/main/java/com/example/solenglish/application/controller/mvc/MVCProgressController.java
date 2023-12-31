package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.TopicService;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/progress")
public class MVCProgressController {

    private final UserService userService;
    private final TopicService topicService;

    public MVCProgressController(UserService userService,
                                 TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }


    @GetMapping("/{id}")
    public String getUserProgress(Model model) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        List<Integer> resultList = topicService.getUserProgress(user);
        model.addAttribute("resultList", resultList);
        return "/users/viewUserProgress";
    }


}
