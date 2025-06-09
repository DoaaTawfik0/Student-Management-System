package com.LearnSpringBoot.Student.Management.System.Course;


import com.LearnSpringBoot.Student.Management.System.Student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, message = "name of course must have at least 3 characters!!")
    private String name;

    @Size(min = 10, message = "name of course must have at least 10 characters!!")
    private String description;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();


    public Course() {
    }

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @JsonProperty("courseDescription")
    public @Size(min = 10, message = "name of course must have at least 10 characters!!") String getDescription() {
        return description;
    }

    @JsonProperty("courseName")
    public @Size(min = 3, message = "name of course must have at least 3 characters!!") String getName() {
        return name;
    }

    @JsonProperty("courseId")
    public int getId() {
        return id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@Size(min = 3, message = "name of course must have at least 3 characters!!") String name) {
        this.name = name;
    }

    public void setDescription(@Size(min = 10, message = "name of course must have at least 10 characters!!") String description) {
        this.description = description;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
