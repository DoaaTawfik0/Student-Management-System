package com.LearnSpringBoot.Student.Management.System.Book;


import com.LearnSpringBoot.Student.Management.System.Student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 8, message = "Title must have at least 8 characters!!")
    private String title;

    @Size(min = 6, message = "Author name must have at least 6 characters!!")
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //Skipping the field in JSON
    @JoinColumn(name = "student_id", nullable = true) // This creates a foreign key column in the Book table
    private Student student;

    public Book() {
    }

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @JsonProperty("bookId")
    public int getId() {
        return id;
    }

    @JsonProperty("bookTitle")
    public @Size(min = 8, message = "Title must have at least 8 characters!!") String getTitle() {
        return title;
    }


    @JsonProperty("bookAuthor")
    public @Size(min = 6, message = "Author name must have at least 6 characters!!") String getAuthor() {
        return author;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@Size(min = 8, message = "Title must have at least 8 characters!!") String title) {
        this.title = title;
    }

    public void setAuthor(@Size(min = 6, message = "Author name must have at least 6 characters!!") String author) {
        this.author = author;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


}
