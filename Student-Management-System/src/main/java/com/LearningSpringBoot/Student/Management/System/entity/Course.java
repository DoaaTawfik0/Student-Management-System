package com.LearningSpringBoot.Student.Management.System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, message = "name of course must have at least 3 characters !!")
    private String name;

    @Size(min = 10, message = "name of course must have at least 10 characters !!")
    private String description;

    @ManyToMany(mappedBy = "studentCourses")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    //ParameterLess Constructor
    public Course() {
    }

    //Parametrized Constructor
    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Course(int id, String name, String description, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @Size(min = 3, message = "name of course must have at least 3 characters !!") String getName() {
        return name;
    }

    public void setName(@Size(min = 3, message = "name of course must have at least 3 characters !!") String name) {
        this.name = name;
    }

    public @Size(min = 10, message = "name of course must have at least 10 characters !!") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "name of course must have at least 10 characters !!") String description) {
        this.description = description;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
