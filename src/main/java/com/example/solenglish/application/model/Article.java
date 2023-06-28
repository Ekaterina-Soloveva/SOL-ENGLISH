package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;


@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "article_sequence", allocationSize = 1)
public class Article extends GenericModel {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "key_words")
    private String keyWords;

    @Column(name = "author")
    private String author;

    @Column(name = "introduction", columnDefinition ="text")
    private String introduction;

    @Column(name = "body", columnDefinition ="text")
    private String body;

    @Column(name = "conclusion", columnDefinition ="text")
    private String conclusion;

}
