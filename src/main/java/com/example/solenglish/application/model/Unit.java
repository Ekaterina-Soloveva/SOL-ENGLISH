package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "unit_sequence", allocationSize = 1)
public class Unit extends GenericModel {

    @Column(name = "title", nullable = false)
    private String title;


    @OneToMany(mappedBy = "unit")
    @Column(name = "unit_topics")
    private List<Topic> topics;

    @ManyToOne
    @JoinColumn(name = "test_id",
            foreignKey = @ForeignKey(name = "FK_UNIT_TEST"))
    private Test test;

    public void setTest(Optional<Test> byId) {
        this.setTest(byId);
    }
}
