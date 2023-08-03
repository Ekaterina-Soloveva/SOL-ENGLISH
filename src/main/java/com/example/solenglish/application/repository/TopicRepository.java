package com.example.solenglish.application.repository;

import com.example.solenglish.application.model.Topic;
import com.example.solenglish.application.model.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TopicRepository
        extends GenericRepository<Topic> {

//        @Query(
//            value = "SELECT * FROM public.topics  WHERE level = ",
//            nativeQuery = true)
//        Collection<Topic> findAllActiveUsersNative();


    List<Topic> findAllByLevelEqualsIgnoreCase(String level);



}
