package com.nuracell.bs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "Book")
@Table(name = "book")
@NoArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence_generator",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "book_name",
            nullable = false
    )
    private String bookName;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "book_student_id_fk"
            )
    )
    @ManyToOne
    private Student student;

    public Book(String bookName, LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.bookName = bookName;
    }
}
