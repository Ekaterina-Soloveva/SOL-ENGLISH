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
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/progress")
public class MVCProgressController {

    private final UserService userService;
    private final TopicService topicService;

    public MVCProgressController(UserService userService,
                                 TopicService topicService
                                   ) {
        this.userService = userService;
        this.topicService = topicService;
    }


    @GetMapping("/{id}")
    public String getUserProgress(Model model) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        model.addAttribute("user", user);


        List<TopicDTO> topicsDone = new ArrayList<>(topicService.getUserTopicsDoneDTO(user.getTopicsDone()));
        List<TopicDTO> topicsPlanned = topicService.getUserTopicsPlanned(topicsDone);

        model.addAttribute("topicsDone", topicsDone);
        model.addAttribute("topicsPlanned", topicsPlanned);

        return "/users/viewUserProgress";
    }



    @GetMapping("/result")
    public String getResult(Model model) {
        model.addAttribute("entranceTestResultForm", new UserDTO());
        return "registration";
    }

}
