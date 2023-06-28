package com.example.solenglish.application.dto;

import com.example.solenglish.application.model.Unit;
import com.example.solenglish.application.model.User;
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
    private String level;
    private String description;
    private List<Long> userTopicsDone;
}

