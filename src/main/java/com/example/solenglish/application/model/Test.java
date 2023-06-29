package com.example.solenglish.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "test_sequence", allocationSize = 1)
public class Test extends GenericModel {

    @Column(name = "test_number", nullable = false)
    private Integer testNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_tasks")
    private int numberOfTasks;

    @Column(name = "number_of_correct_tasks")
    private Integer numberOfCorrectTasks;

    @OneToMany(mappedBy = "test")
    @Column(name = "units")
    private List<Unit> units;


    @ManyToMany
    @JoinTable(name = "users_tests",
            joinColumns = @JoinColumn(name = "test_id"), foreignKey = @ForeignKey(name = "FK_TESTS_USERS"),
            inverseJoinColumns = @JoinColumn(name = "user_id"), inverseForeignKey = @ForeignKey(name ="FK_USERS_TESTS"))
    private List<User> users;




}