package com.example.solenglish.application.mapper;

import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.model.GenericModel;
import com.example.solenglish.application.model.Topic;
import com.example.solenglish.application.model.Unit;
import com.example.solenglish.application.repository.TopicRepository;
import com.example.solenglish.application.repository.UnitRepository;
import com.example.solenglish.application.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TopicMapper extends GenericMapper<Topic, TopicDTO> {


    private final UserRepository userRepository;

    public TopicMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(Topic.class, TopicDTO.class, modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    protected void mapSpecificFields(TopicDTO source, Topic destination) {
        if (!Objects.isNull(source.getUserTopicsDone())) {
            destination.setUserTopicsDone(userRepository.findAllById(source.getUserTopicsDone()));
        } else {
            destination.setUserTopicsDone(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(Topic source, TopicDTO destination) {
        destination.setUserTopicsDone(getIds(source));
    }


    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Topic.class, TopicDTO.class)
                .addMappings(m -> m.skip(TopicDTO::setUserTopicsDone)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(TopicDTO.class, Topic.class)
                .addMappings(m -> m.skip(Topic::setUserTopicsDone)).setPostConverter(toEntityConverter());
    }


    protected List<Long> getIds(Topic entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUserTopicsDone())
                ? null
                : entity.getUserTopicsDone().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}
