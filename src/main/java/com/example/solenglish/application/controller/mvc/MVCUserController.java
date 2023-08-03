package com.example.solenglish.application.controller.mvc;


import com.example.solenglish.application.constants.Errors;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.exception.CustomException;
import com.example.solenglish.application.service.UserService;
import com.example.solenglish.application.service.userdetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.solenglish.application.constants.UserRolesConstants.ADMIN;


@Controller
@Slf4j
@RequestMapping("/users")
public class MVCUserController {
    Logger logger = LoggerFactory.getLogger("com.example.solenglish.application");

    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewAllUsers(Model model) {
        List<UserDTO> users = userService.listAll();

        model.addAttribute("usersAndTeachers", users);
        model.addAttribute("users", users.stream()
                .filter(e -> e.getRole().getTitle().equalsIgnoreCase("user"))
                .collect(Collectors.toList()));
        return "/users/viewAllUsers";
    }

    @PostMapping("/search")
    public String searchUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @ModelAttribute("userSearchForm") UserDTO userDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "first_name"));
        model.addAttribute("usersAndTeachers", userService.findUsers(userDTO, pageRequest));
        return "users/viewAllUsers";
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
            return Errors.Users.USER_NOT_FOUND_ERROR;
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
        logger.info("Пользователь " + userDTO.getId() + "изменил пароль");
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
        return "/users/changeUserProfile";
    }

    @PostMapping("/changeProfile")
    public String changeProfile(@ModelAttribute("changeProfileForm") UserDTO userDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        userDTO.setRole(user.getRole());
        userService.update(userDTO);
        return "redirect:/users/profile/ + customUserDetails.getUserId()";
    }

    @ExceptionHandler({CustomException.class, NotFoundException.class})
    public RedirectView handleError(HttpServletRequest request,
                                    Exception exception,
                                    RedirectAttributes redirectAttributes) {
        log.error("Запрос " + request.getRequestURL() + " вызвал ошибку: " + exception.getMessage());
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return new RedirectView("/", true);
    }


    @GetMapping("/addTeacher/{id}")
    public String addTeacher(@PathVariable Long id) {
        UserDTO user = userService.getOne(id);

        if (!user.getRole().getTitle().equalsIgnoreCase("teacher")) {
            logger.info("Назначен новый преподаватель " + user.getId());
            userService.addTeacher(user);
        }
        return "redirect:/users";
    }


}






