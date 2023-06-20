//package com.example.solenglish.application.controller.mvc;
//
//
//import com.example.solenglish.application.dto.CurriculumDTO;
//import com.example.solenglish.application.dto.UserDTO;
//import com.example.solenglish.application.service.CurriculumService;
//import com.example.solenglish.application.service.UserService;
//import com.example.solenglish.application.service.userdetails.CustomUserDetails;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.solenglish.application.constants.UserRolesConstants.ADMIN;

//@Controller
//@Slf4j
//@RequestMapping("/entranceTest")
//public class MVCEntranceTestController {
//
//    private final UserService userService;
//    private final CurriculumService curriculumService;
//
//    public MVCEntranceTestController(UserService userService, CurriculumService curriculumService) {
//        this.userService = userService;
//        this.curriculumService = curriculumService;
//    }
//
//    @GetMapping("/")
//    public String registration(Model model) {
//        model.addAttribute("entranceForm", new CurriculumDTO());
//        return "/";
//    }
//
////    @PostMapping("/")
////    public String registration(@ModelAttribute("entranceForm") CurriculumDTO newCurriculum) {
////
////        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
////        if (user.getCurriculum = null) {
////            curriculumService.create(newCurriculum, user);
////            return "redirect:entranceTest";
////        } else {
////            return "redirect:progress";
////        }
////    }
//
//    @GetMapping("/result")
//    public String getResult(Model model) {
//        model.addAttribute("entranceTestResultForm", new UserDTO());
//        return "registration";
//    }
//
//}
