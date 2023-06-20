package com.example.solenglish.application.controller.mvc;


import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

import static com.example.solenglish.application.constants.UserRolesConstants.ADMIN;


@Controller
@Slf4j
@RequestMapping("/users")
public class MVCUserController {

    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewAllUsers(Model model) {
        List<UserDTO> users = userService.listAll();
        model.addAttribute("users", users);
        return "/users/viewAllUsers";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой e-mail уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:login";
    }


    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }

    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "Error!";                         //TODO: Error!
        } else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }


    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }



    @GetMapping("/profile/{id}")
    public String getUserProfile(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        model.addAttribute("user", user);
        return "/users/viewUserProfile";
    }



    @GetMapping("/changeProfile")
    public String changeProfile(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        model.addAttribute("user", user);
        return "/users/changeUserProfile" ;
    }

    @PostMapping("/changeProfile")
    public String changeProfile(@ModelAttribute("changeProfileForm") UserDTO userDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        userDTO.setRole(user.getRole());
        userService.update(userDTO);
        return "redirect:/users/profile/ + customUserDetails.getUserId()";
    }
}






