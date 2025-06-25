package com.LearningSpringBoot.Student.Management.System.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 6, message = "Title of book must have at at least 6 characters !!")
    private String bookTitle;

    @Size(min = 6, message = "Author name must have at least 6 characters !!")
    private String bookAuthor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //Skipping the field in JSON
    @JoinColumn(name = "student_id", nullable = true) // This creates a foreign key column in the Book table
    private Student student;

    //ParameterLess Constructor
    public Book() {
    }

    //Parametrized Constructor
    public Book(int id, String bookTitle, String bookAuthor) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @Size(min = 6, message = "Title of book must have at at least 6 characters !!") String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(@Size(min = 6, message = "Title of book must have at at least 6 characters !!") String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public @Size(min = 6, message = "Author name must have at least 6 characters !!") String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(@Size(min = 6, message = "Author name must have at least 6 characters !!") String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
