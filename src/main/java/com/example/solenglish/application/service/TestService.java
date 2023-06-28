package com.example.solenglish.application.service;

import com.example.solenglish.application.dto.TestDTO;
import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UnitDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.Test;
import com.example.solenglish.application.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TestService extends GenericService<Test, TestDTO> {

    private final TopicService topicService;
    private final UserService userService;


    public TestService(GenericRepository<Test> repository,
                       GenericMapper<Test, TestDTO> mapper,
                       TopicService topicService, UserService userService) {
        super(repository, mapper);
        this.topicService = topicService;
        this.userService = userService;

    }


    /**
     *
     * @param updateObject
     * @param correctAnswersNumber
     * @return пользователя с обновленным списком усвоенных тем по результатам входного тестирования
     */
    public UserDTO makeUserCurriculum(UserDTO updateObject, int correctAnswersNumber) {
        int numberOfQuestions = repository.findById(1L).get().getNumberOfTasks();
        List<TopicDTO> topicsDone = checkEntranceTest(numberOfQuestions, correctAnswersNumber);
        updateObject.setTopicsDone(topicsDone.stream().map(e -> e.getId().longValue()).toList());
        List<Long> testsDone= new ArrayList<>();
        testsDone.add(1L);
        updateObject.setTests(testsDone);
        userService.updateUserTopicsDone(updateObject);
        return updateObject;
    }

    /**
     *
     * @param numberOfQuestions
     * @param correctAnswersNumber
     * @return Список усвоенных тем по результатам входного тестирования
     */
    private List<TopicDTO> checkEntranceTest(int numberOfQuestions, int correctAnswersNumber) {
        int difference = numberOfQuestions - correctAnswersNumber;
        List<TopicDTO> topicsDone = new ArrayList<>();
        if (difference <= 2) {
            topicsDone.addAll(topicService.getTopicsByLevel("B2 - Upper-Intermediate"));
        } else if (difference == 4 || difference == 3) {
            topicsDone.addAll(topicService.getTopicsByLevel("B1 - Intermediate"));
        } else if (difference == 5 || difference == 6) {
            topicsDone.addAll(topicService.getTopicsByLevel("A2 - Elementary"));
        } else if (difference == 7 || difference == 8) {
            topicsDone.addAll(topicService.getTopicsByLevel("A1 - Beginner"));
        } else {
            topicsDone.addAll(topicService.listAll());
        }

        return topicsDone;
    }

}
