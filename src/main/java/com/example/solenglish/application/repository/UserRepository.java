package com.example.solenglish.application.repository;

import com.example.solenglish.application.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends GenericRepository<User> {
    //    @Query(nativeQuery = true,
//            value = "select * from users where login = :login")
    User findUserByLogin(String login);

    User findUserByEmail(String email);

    User getUserByPhone(String phone);

    User findUserByChangePasswordToken(String uuid);

}


