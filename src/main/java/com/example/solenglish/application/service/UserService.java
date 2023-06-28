package com.example.solenglish.application.service;

import com.example.solenglish.application.constants.MailConstants;
import com.example.solenglish.application.dto.RoleDTO;
import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.User;
import com.example.solenglish.application.repository.GenericRepository;
import com.example.solenglish.application.repository.UserRepository;
import com.example.solenglish.application.utils.MailUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService
        extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return mapper.toDTO(((UserRepository) repository).findUserByEmail(email));
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        log.info("Generated Token: {} ", uuid);

        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);

        SimpleMailMessage mailMessage = MailUtils.createMailMessage(
                userDTO.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid
        );

        javaMailSender.send(mailMessage);

    }

    public void changePassword(String uuid, String password) {
        UserDTO userDTO = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }

    public UserDTO updateUserTopicsDone(UserDTO updateObject) {
        return mapper.toDTO(repository.save(mapper.toEntity(updateObject)));
    }




}

