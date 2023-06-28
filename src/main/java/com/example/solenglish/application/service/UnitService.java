package com.example.solenglish.application.service;


import com.example.solenglish.application.dto.TopicDTO;
import com.example.solenglish.application.dto.UnitDTO;
import com.example.solenglish.application.mapper.GenericMapper;
import com.example.solenglish.application.model.Topic;
import com.example.solenglish.application.model.Unit;
import com.example.solenglish.application.repository.GenericRepository;
import com.example.solenglish.application.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UnitService extends GenericService<Unit, UnitDTO> {
    private final TopicService topicService;
    private final UnitRepository unitRepository;


    public UnitService(GenericRepository<Unit> repository,
                       GenericMapper<Unit, UnitDTO> mapper,
                       TopicService topicService, UnitRepository unitRepository) {
        super(repository, mapper);
        this.topicService = topicService;
        this.unitRepository = unitRepository;
    }

    public List<UnitDTO> getAllUnits() {
        List<Unit> allUnits= unitRepository.findAll();
        return mapper.toDTOs(allUnits);
    }

}
