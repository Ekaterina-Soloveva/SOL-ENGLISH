package com.example.solenglish.application.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TopicDTO extends GenericDTO{

    private String title;
    private LevelDTO level;
    private TestDTO test;

}

