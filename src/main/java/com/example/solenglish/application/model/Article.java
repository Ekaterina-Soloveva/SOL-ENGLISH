package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(name = "content")
    private String content;


}
