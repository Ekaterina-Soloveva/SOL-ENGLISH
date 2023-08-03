package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.ContactFormDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import static com.example.solenglish.application.constants.UserRolesConstants.ADMIN;

@Controller
public class MVCMainController {

    private final UserService userService;

    public MVCMainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String getInfoAbout() {
        return "infoAboutCompany";
    }

    @GetMapping("/contact")
    public String getContactForm(Model model) {
        model.addAttribute("contactForm", new ContactFormDTO());
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        model.addAttribute("user", user);
        return "contactForm";
    }

    @PostMapping("/contact")
    public String getContactForm(@ModelAttribute("contactForm") ContactFormDTO contactFormDTO) {
        contactFormDTO.setDate(LocalDate.now());
        userService.sendEmailFromUser(contactFormDTO);
        return "redirect:/";
    }





}



