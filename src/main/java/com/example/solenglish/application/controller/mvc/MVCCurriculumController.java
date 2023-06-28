package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UnitDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.TopicService;
//import com.example.solenglish.application.service.UnitService;
import com.example.solenglish.application.service.UnitService;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/curriculum")
public class MVCCurriculumController {

    private final UserService userService;
    private final TopicService topicService;
    private final UnitService unitService;

    public MVCCurriculumController(UserService userService,
                                   TopicService topicService,
                                   UnitService unitService) {
        this.userService = userService;
        this.topicService = topicService;
        this.unitService = unitService;
    }


    @GetMapping("/{id}")
    public String getUserCurriculum(Model model) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        model.addAttribute("user", user);

        List<TopicDTO> topicsDone = new ArrayList<>(topicService.getUserTopicsDoneDTO(user.getTopicsDone()));
        List<UnitDTO> units = unitService.getAllUnits();

        model.addAttribute("topicsDone", topicsDone);
        model.addAttribute("units", units);

        return "/topics/viewUserCurriculum";
    }


    @GetMapping("/viewTopic/{id}")
    public String getOneTopic(@PathVariable Long id, Model model) {
        model.addAttribute("topic", topicService.getOne(id));
        return "topics/viewTopic";
    }

    @PostMapping("/viewTopic/{id}")
    public String getOneTopic(@ModelAttribute("topicForm")
                              @PathVariable Long id) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));

        List<Long> userTopicsDone = user.getTopicsDone();
        userTopicsDone.add(id);
        user.setTopicsDone(userTopicsDone);

        userService.update(user);
        return "redirect:/topics/viewUserCurriculum";
    }

}
