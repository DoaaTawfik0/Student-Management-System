package com.LearningSpringBoot.Student.Management.System.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "students")
@JsonPropertyOrder({"id", "name", "age", "email", "courses", "books"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, message = "Name of student must have at least 3 characters !!")
    private String studentName;

    @Email(message = "Email must have a valid domain !!")
    @NotBlank
    private String studentEmail;

    @Min(value = 10, message = "Age must be at least 10")
    @Max(value = 30, message = "Age must not exceed 30")
    private int studentAge;

    // 'student' refers to the field in Course , applying cascading
    @OneToMany(mappedBy = "student")
    private List<Book> studentBooks;

    @ManyToMany()
    @JoinTable(
            name = "student_course", // Join table name
            joinColumns = @JoinColumn(name = "student_id"), // FK for Student
            inverseJoinColumns = @JoinColumn(name = "course_id") // FK for Course
    )
    private Set<Course> studentCourses = new HashSet<>();


    //Parametrized Constructor
    public Student(int id, String studentName, String studentEmail, int studentAge) {
        this.id = id;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentAge = studentAge;
    }

    public Set<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Book> getStudentBooks() {
        return studentBooks;
    }

    public void setStudentBooks(List<Book> studentBooks) {
        this.studentBooks = studentBooks;
    }

    @Min(value = 10, message = "Age must be at least 10")
    @Max(value = 30, message = "Age must not exceed 30")
    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(@Min(value = 10, message = "Age must be at least 10") @Max(value = 30, message = "Age must not exceed 30") int studentAge) {
        this.studentAge = studentAge;
    }

    public @Email(message = "Email must have a valid domain !!") String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(@Email(message = "Email must have a valid domain !!") String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public @Size(min = 3, message = "Name of student must have at least 3 characters !!") String getStudentName() {
        return studentName;
    }

    public void setStudentName(@Size(min = 3, message = "Name of student must have at least 3 characters !!") String studentName) {
        this.studentName = studentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}