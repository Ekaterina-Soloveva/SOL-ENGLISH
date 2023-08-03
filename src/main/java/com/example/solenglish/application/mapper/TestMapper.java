package com.example.solenglish.application.mapper;

import com.example.solenglish.application.dto.TestDTO;
import com.example.solenglish.application.dto.UserDTO;
import com.example.solenglish.application.model.GenericModel;
import com.example.solenglish.application.model.Test;
import com.example.solenglish.application.model.User;
import com.example.solenglish.application.repository.TestRepository;
import com.example.solenglish.application.repository.UnitRepository;
import com.example.solenglish.application.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TestMapper extends GenericMapper<Test, TestDTO> {
    private final TestRepository testRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;


    public TestMapper(ModelMapper modelMapper, TestRepository testRepository,
                      UnitRepository unitRepository, UserRepository userRepository) {
        super(Test.class, TestDTO.class, modelMapper);
        this.testRepository = testRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void mapSpecificFields(TestDTO source, Test destination) {
        if (!Objects.isNull(source.getUnits())) {
            destination.setUnits(unitRepository.findAllById(source.getUnits()));
        } else {
            destination.setUnits(Collections.emptyList());
        }
        if (!Objects.isNull(source.getUsers())) {
            destination.setUsers(userRepository.findAllById(source.getUsers()));
        } else {
            destination.setUsers(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(Test source, TestDTO destination) {
        destination.setUnits(getUnitsIds(source));
        destination.setUsers(getUsersIds(source));
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Test.class, TestDTO.class)
                .addMappings(m -> m.skip(TestDTO::setUnits))
                .addMappings(m -> m.skip(TestDTO::setUsers)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(TestDTO.class, Test.class)
                .addMappings(m -> m.skip(Test::setUnits))
                .addMappings(m -> m.skip(Test::setUsers)).setPostConverter(toEntityConverter());
    }

    protected List<Long> getUnitsIds(Test entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUnits())
                ? null
                : entity.getUnits().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }

    protected List<Long> getUsersIds(Test entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUsers())
                ? null
                : entity.getUsers().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }

}








