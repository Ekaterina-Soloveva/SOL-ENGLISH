package com.example.solenglish.application.mapper;


import com.example.solenglish.application.dto.GenericDTO;
import com.example.solenglish.application.model.GenericModel;

import java.util.List;

public interface Mapper <E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}
