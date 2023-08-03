package com.example.solenglish.application.dto;

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
public class ContactFormDTO {
    private String login;
    private String email;
    private LocalDate date;
    private String firstName;
    private String lastName;
    private String phone;
    private List<String> content;

}
