package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "topic_sequence", allocationSize = 1)
public class Topic extends GenericModel {

    @Column(name = "title", nullable = false)
    private String title;


    @ManyToOne
    @JoinColumn(name = "unit_id",
            foreignKey = @ForeignKey(name = "FK_TOPICS_UNIT"))
    private Unit unit;


    @ManyToMany
    @JoinTable(name = "user_topics_done",
            joinColumns = @JoinColumn(name = "topic_id"), foreignKey = @ForeignKey(name = "FK_TOPICS_USERS"),
            inverseJoinColumns = @JoinColumn(name = "user_id"), inverseForeignKey = @ForeignKey(name ="FK_USERS_TOPICS"))
    private List<User> userTopicsDone;


}

