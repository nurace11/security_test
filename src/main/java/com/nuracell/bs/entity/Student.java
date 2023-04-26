package com.nuracell.bs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    @OneToOne(
            mappedBy = "student",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private List<Book> books;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinTable(
            name = "enrolment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
            )

    )
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    public void enrolToCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }

    public void unEnrolCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }

        if (!this.books.contains(book)) {
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
}
