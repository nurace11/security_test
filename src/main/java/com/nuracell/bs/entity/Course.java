package com.nuracell.bs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_seq_generator",
            sequenceName = "course_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_seq_generator"
    )
    private Long id;

    private String name;

    private String department;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @ToString.Exclude
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addEnrolment(Enrolment enrolment) {
        if (!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        enrolments.remove(enrolment);
    }

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }
}
