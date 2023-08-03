package com.example.solenglish.application.repository;

import com.example.solenglish.application.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository
        extends GenericRepository<User> {

    User findUserByLogin(String login);

    User findUserByEmail(String email);

    User getUserByPhone(String phone);

    User findUserByChangePasswordToken(String uuid);

     @Query(nativeQuery = true,
            value = """
                 select u.*
                 from users u
                 where u.first_name ilike '%' || coalesce(:firstName, '%') || '%'
                 and u.last_name ilike '%' || coalesce(:lastName, '%') || '%'
                 and u.login ilike '%' || coalesce(:login, '%') || '%'
                  """)
     Page<User> searchUsers(String firstName,
                            String lastName,
                            String login,
                            Pageable pageable);


    @Query(nativeQuery = true,
            value = """
                 select distinct u.*
                 from users u left join user_topics_done utd on u.id = utd.user_id
                 inner join topics t on t.id = utd.topic_id
                 where t.level ilike '%'  || :level  ||  '%' order by u.last_name asc 
                  """)
    List<User> getUsersByLevel(String level);



}


