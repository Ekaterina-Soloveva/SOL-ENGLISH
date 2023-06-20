package com.example.solenglish.application.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO extends GenericDTO {

    private String title;
    private String keyWords;
    private String content;
}
