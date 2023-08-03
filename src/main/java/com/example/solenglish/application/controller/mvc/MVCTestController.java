package com.example.solenglish.application.controller.mvc;


import com.example.solenglish.application.constants.Errors;
import com.example.solenglish.application.dto.TestDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.exception.CustomException;
import com.example.solenglish.application.service.TestService;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.util.*;
import java.util.stream.Collectors;


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

    /**
     * @param model
     * @return страницу с входным тестированием
     */
    @GetMapping("/entranceTest")
    public String getEntranceTest(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        if (!user.getTopicsDone().isEmpty())
            model.addAttribute("exception", Errors.Tests.ENTRANCE_TEST_ERROR);
        TestDTO entranceTest = testService.getOne(1L);
        model.addAttribute("entranceTest", entranceTest);
        return "/tests/entranceTest";
    }

    /**
     * @param test
     * @return страницу с учебным планом на основе тестирования
     */

    @PostMapping("/entranceTest")
    public String getEntranceTest(@ModelAttribute("entranceTest") TestDTO test) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));

        try {
            testService.makeUserCurriculum(user, test);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/curriculum/ + customUserDetails.getUserId()";

    }



    /**
     * @param model
     * @return страницу с пройденными и планируемыми контрольными работами
     */

    @GetMapping("/viewAllTests")
    public String getAllTests(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));

        List<TestDTO> userTests = testService.getTestsByIds(user.getTests());
        List<TestDTO> plannedTests = testService.listAll().stream().filter(e -> !userTests.contains(e)).collect(Collectors.toList());

        model.addAttribute("userTests", userTests.stream()
                .sorted(Comparator.comparingInt(TestDTO::getTestNumber)));
        model.addAttribute("plannedTests", plannedTests.stream()
                .sorted(Comparator.comparingInt(TestDTO::getTestNumber)));
        return "/tests/viewAllTests";
    }

}
