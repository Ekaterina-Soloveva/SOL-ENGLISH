package com.example.solenglish.application.service;

import com.example.solenglish.application.constants.Errors;
import com.example.solenglish.application.dto.TestDTO;
import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UnitDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.exception.CustomException;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.Test;
import com.example.solenglish.application.model.Topic;
import com.example.solenglish.application.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     * @param updateObject
     * @param test
     * @return пользователя с обновленным списком усвоенных тем по результатам входного тестирования
     */
    public UserDTO makeUserCurriculum(UserDTO updateObject, TestDTO test) throws CustomException {

        if (updateObject.getTopicsDone().isEmpty()) {
            List<TopicDTO> topicsDone = checkEntranceTest(test.getNumberOfTasks(), test.getNumberOfCorrectTasks());
            updateObject.setTopicsDone(topicsDone.stream().map(e -> e.getId().longValue()).toList());
            List<Long> testsDone = new ArrayList<>();
            testsDone.add(test.getId());
            updateObject.setTests(testsDone);
            userService.updateUserTopicsDone(updateObject);
            return updateObject;
        } else {
            throw new CustomException(Errors.Tests.ENTRANCE_TEST_ERROR);
        }
    }


    /**
     * @param numberOfQuestions
     * @param correctAnswersNumber
     * @return Список усвоенных тем по результатам входного тестирования
     */
    private List<TopicDTO> checkEntranceTest(int numberOfQuestions, int correctAnswersNumber) {
        int difference = numberOfQuestions - correctAnswersNumber;
        List<TopicDTO> topicsDone = new ArrayList<>();
        if (difference > 8)
            topicsDone.addAll(Collections.emptyList());
        if (difference < 8)
            topicsDone.addAll(topicService.getTopicsByLevel("A1 - Beginner"));
        if (difference < 5)
            topicsDone.addAll(topicService.getTopicsByLevel("A2 - Elementary"));
        if (difference < 3)
            topicsDone.addAll(topicService.getTopicsByLevel("B1 - Intermediate"));
        return topicsDone;
    }


    public List<TestDTO> getTestsByIds(List<Long> testsIds) {
        List<Test> resultList = repository.findAllById(testsIds);
        return mapper.toDTOs(resultList);
    }

}
