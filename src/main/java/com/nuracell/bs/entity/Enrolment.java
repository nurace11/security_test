package com.nuracell.bs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
@NoArgsConstructor
@Data
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("studentId") // maps to studentId field in EnrolmentId
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "enrolment_student_id_fk"))
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "enrolment_course_id_fk"))
    private Course course;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            nullable = false
    )
    private LocalDateTime localDateTime;

    public Enrolment(Student student, Course course, LocalDateTime localDateTime) {
        this.student = student;
        this.course = course;
        this.localDateTime = localDateTime;
    }

    public Enrolment(EnrolmentId id, Student student, Course course, LocalDateTime now) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.localDateTime = now;
    }
}
