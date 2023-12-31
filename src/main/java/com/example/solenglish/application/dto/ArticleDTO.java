package com.example.solenglish.application.dto;

import jakarta.persistence.Column;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDTO extends GenericDTO {

    private String title;
    private String keyWords;
    private String author;
    private String introduction;
    private String body;
    private String conclusion;

}
