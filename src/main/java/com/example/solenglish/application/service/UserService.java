package com.example.solenglish.application.service;

import com.example.solenglish.application.constants.MailConstants;
import com.example.solenglish.application.dto.ContactFormDTO;
import com.example.solenglish.application.dto.RoleDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.User;
import com.example.solenglish.application.repository.GenericRepository;
import com.example.solenglish.application.repository.UserRepository;
import com.example.solenglish.application.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService
        extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger("com.example.solenglish.application");

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    /**
     *
     * @param newObject
     * @return созданного пользователя
     */
    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    /**
     *
     * @param login
     * @return пользователя, найденного по логину
     */
    public UserDTO getUserByLogin(final String login) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(login));
    }

    /**
     *
     * @param email
     * @return пользователя, найденного по адресу электронной почты
     */
    public UserDTO getUserByEmail(final String email) {
        return mapper.toDTO(((UserRepository) repository).findUserByEmail(email));
    }

    /**
     *
     * @param password
     * @param foundUser
     * @return true, если пароль верный
     */
    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

    /**
     *
     * @param userDTO
     * высылает ключ для изменения пароля
     */
    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        logger.info("Generated Token: {} ", uuid);

        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);

        SimpleMailMessage mailMessage = MailUtils.createMailMessage(
                userDTO.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid
        );

        javaMailSender.send(mailMessage);

    }

    /**
     *
     * @param uuid
     * @param password
     * измененяет пароль пользовотеля
     */
    public void changePassword(String uuid, String password) {
        UserDTO userDTO = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }

    /**
     *
     * @param updateObject
     * @return пользователя, у которого обновлен список пройденных тем
     */
    public UserDTO updateUserTopicsDone(UserDTO updateObject) {
        return mapper.toDTO(repository.save(mapper.toEntity(updateObject)));
    }


    /**
     *
     * @param contactFormDTO
     * отправляет писмо от пользовотеля в поддержку
     */
    public void sendEmailFromUser(ContactFormDTO contactFormDTO) {
        SimpleMailMessage mail = MailUtils.createMailMessageFromUser(contactFormDTO);
        logger.info("Sent mail from user: {} ", contactFormDTO.getLogin());
        javaMailSender.send(mail);
    }

    /**
     *
     * @param userDTO
     * @param pageable
     * @return страницу пользователей, которые совпали по параметрам
     */
    public Page<UserDTO> findUsers(UserDTO userDTO,
                                   Pageable pageable) {
        Page<User> users = ((UserRepository) repository).searchUsers(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getLogin(),
                pageable);
        List<UserDTO> result = mapper.toDTOs(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    /**
     *
     * @param newObject
     * @return пользователя, котрый назначен преподавателем
     */
    public UserDTO addTeacher(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(2L);
        newObject.setRole(roleDTO);
        return update(newObject);
    }


    /**
     *
     * @param level
     * @return пользователей, которые темы уровня
     */
    public List<User> getUserDTOsByLevel(String level) {
        List<User> usersByLevel = ((UserRepository) repository).getUsersByLevel(level);
        System.out.println(usersByLevel);
        return usersByLevel;
    }



}

