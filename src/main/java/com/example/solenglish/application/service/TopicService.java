package com.example.solenglish.application.service;

import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.Topic;
import com.example.solenglish.application.model.User;
import com.example.solenglish.application.repository.GenericRepository;
import com.example.solenglish.application.repository.TopicRepository;
import com.example.solenglish.application.repository.UnitRepository;
import com.example.solenglish.application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TopicService extends GenericService<Topic, TopicDTO> {

    private final UnitRepository unitRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(GenericRepository<Topic> repository, GenericMapper<Topic,
            TopicDTO> mapper, UnitRepository unitRepository,
                        TopicRepository topicRepository, UserRepository userRepository) {
        super(repository, mapper);
        this.unitRepository = unitRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param level
     * @return список тем, где уровень совпадет с параметром
     */

    public List<TopicDTO> getTopicsByLevel(String level) {
        List<Topic> topicsByLevel = topicRepository.findAllByLevelEqualsIgnoreCase(level);
        return mapper.toDTOs(topicsByLevel);
    }


    /**
     * @param userTopicsDone
     * @return список TopicDTO - тем, которые были пройдены
     */
    public List<TopicDTO> getUserTopicsDoneDTO(List<Long> userTopicsDone) {
        List<Topic> topics = topicRepository.findAllById(userTopicsDone);
        return mapper.toDTOs(topics);
    }

    /**
     * @param userTopicsDone
     * @return список TopicDTO - оставшихся тем, которые были не пройдены
     */
    public List<TopicDTO> getUserTopicsPlanned(List<TopicDTO> userTopicsDone) {
        List<TopicDTO> listOfAllTopics = new ArrayList<>(getAllTopics());

        List<TopicDTO> topicsPlanned = listOfAllTopics.stream()
                .filter(element -> !userTopicsDone.contains(element))
                .collect(Collectors.toList());
        return topicsPlanned;
    }


    /**
     * @return Все темы, которые есть в БД
     */
    public List<TopicDTO> getAllTopics() {
        List<Topic> allTopics = topicRepository.findAll();
        return mapper.toDTOs(allTopics);
    }

    /**
     * @param topicsIds
     * @return список тем по Id
     */
    public List<TopicDTO> getTopicsByIds(List<Long> topicsIds) {
        List<Topic> resultList = topicRepository.findAllById(topicsIds);
        return mapper.toDTOs(resultList);
    }

    /**
     * @param user
     * @return список с количеством тем пройденных пользователем по уровням
     */
    public List<Integer> getUserProgress(UserDTO user) {

        List<Long> allTopicsByLevel = getNumberOfTopicsByLevel(getAllTopics());
        List<Long> allUserTopicsByLevel = getNumberOfTopicsByLevel(getUserTopicsDoneDTO(user.getTopicsDone()));
        List<Integer> userProgressList = new ArrayList<>();

        for (int i = 0; i < allTopicsByLevel.size(); i++) {
            if (allUserTopicsByLevel.get(i) == 0) {
                userProgressList.add(0);
            } else if (allTopicsByLevel.get(i) == allUserTopicsByLevel.get(i)) {
                userProgressList.add(100);
            } else {
                userProgressList.add((int) (allUserTopicsByLevel.get(i) * 1.0 / allTopicsByLevel.get(i) * 100));
            }
        }

        return userProgressList;
    }

    /**
     * @param topicsList
     * @return список с количеством тем по уровням
     */
    public List<Long> getNumberOfTopicsByLevel(List<TopicDTO> topicsList) {
        Map<String, Long> sortedMapOfElements = new TreeMap<>();
        sortedMapOfElements.putAll(topicsList.stream()
                .collect(Collectors.groupingBy(TopicDTO::getLevel, Collectors.counting())));

        List<Long> numberOfTopicsByLevel = new ArrayList<>(sortedMapOfElements.values());

        do {
            numberOfTopicsByLevel.add(0L);
        } while (numberOfTopicsByLevel.size() != 6);

        return numberOfTopicsByLevel;
    }

}
