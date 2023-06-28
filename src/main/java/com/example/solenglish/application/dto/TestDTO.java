package com.example.solenglish.application.dto;


import com.example.solenglish.application.model.Unit;
import com.example.solenglish.application.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestDTO extends GenericDTO {

    private Integer testNumber;
    private String description;
    private int numberOfTasks;
    private Integer numberOfCorrectTasks;
    private List<Long> units;
    private List<Long> users;
}
