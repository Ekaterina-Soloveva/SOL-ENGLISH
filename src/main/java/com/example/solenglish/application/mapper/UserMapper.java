package com.example.solenglish.application.mapper;

import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.model.User;

import com.example.solenglish.application.repository.TestRepository;
import com.example.solenglish.application.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.solenglish.application.model.GenericModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper
        extends GenericMapper<User, UserDTO> {

    private final TopicRepository topicRepository;
    private final TestRepository testRepository;

    protected UserMapper(ModelMapper modelMapper,
                         TopicRepository topicRepository,
                         TestRepository testRepository) {
        super(User.class, UserDTO.class, modelMapper);
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setTopicsDone))
                .addMappings(m -> m.skip(UserDTO::setTests)).setPostConverter(toDTOConverter());

        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setTopicsDone))
                .addMappings(m -> m.skip(User::setTests)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getTopicsDone())) {
            destination.setTopicsDone(topicRepository.findAllById(source.getTopicsDone()));
        } else {
            destination.setTopicsDone(Collections.emptyList());
        }
        if (!Objects.isNull(source.getTests())) {
            destination.setTests(testRepository.findAllById(source.getTests()));
        } else {
            destination.setTests(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setTopicsDone(getTopicsDoneIds(source));
        destination.setTests(getTestsIds(source));
    }


    protected List<Long> getTopicsDoneIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTopicsDone())
                ? null
                : entity.getTopicsDone().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }

    protected List<Long> getTestsIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTests())
                ? null
                : entity.getTests().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}

