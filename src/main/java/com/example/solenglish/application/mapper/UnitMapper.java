package com.example.solenglish.application.mapper;

import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UnitDTO;
import com.example.solenglish.application.model.GenericModel;
import com.example.solenglish.application.model.Unit;

import com.example.solenglish.application.repository.TestRepository;
import com.example.solenglish.application.repository.TopicRepository;
import com.example.solenglish.application.service.TestService;
import com.example.solenglish.application.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UnitMapper extends GenericMapper <Unit, UnitDTO> {

    private final TopicRepository topicRepository;
    private final TopicService topicService;
    private final TestRepository testRepository;
    private final TestService testService;

    public UnitMapper(ModelMapper modelMapper,
                      TopicService topicService,TopicRepository topicRepository,
                      TestRepository testRepository,
                       TestService testService) {
        super(Unit.class, UnitDTO.class, modelMapper);
        this.topicService = topicService;
        this.topicRepository = topicRepository;
        this.testService = testService;
        this.testRepository = testRepository;
    }


    @Override
    protected void mapSpecificFields(UnitDTO source, Unit destination) {

        if (!Objects.isNull(source.getTopics())) {
            destination.setTopics(topicRepository.findAllById(source.getTopics()
                    .stream().map(e -> e.getId().longValue()).toList()));
        } else {
            destination.setTopics(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(Unit source, UnitDTO destination) {
        destination.setTopics(topicService.getTopicsByIds(source.getTopics()
                .stream().map(e -> e.getId().longValue()).toList()));
    }


    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Unit.class, UnitDTO.class)
                .addMappings(m -> m.skip(UnitDTO::setTopics))
                .setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(UnitDTO.class, Unit.class)
                .addMappings(m -> m.skip(Unit::setTopics))
                .setPostConverter(toEntityConverter());

    }

}
