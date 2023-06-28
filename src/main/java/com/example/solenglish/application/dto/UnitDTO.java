package com.example.solenglish.application.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class UnitDTO extends GenericDTO {

    private String title;
    private List<TopicDTO> topics;
    private TestDTO test;
}
