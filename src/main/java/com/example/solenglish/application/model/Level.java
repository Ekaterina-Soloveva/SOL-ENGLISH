package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.solenglish.application.model.Topic;

@Entity
@Table(name = "levels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "level_sequence", allocationSize = 1)
public class Level extends GenericModel {

    @Column(name = "title", nullable = false)
    private String title;

//    @OneToMany(mappedBy = "level")
//    @Column(name = "topics")
//    private List<Topic> topics;

    @OneToMany(mappedBy = "level")
    @Column(name = "units")
    private List<Unit> units;


//    @ManyToMany(mappedBy = "userLevel")
//    private List<User> users;

}

